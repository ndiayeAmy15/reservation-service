package com.examenSPDO.reservation_service.service.impl;

import com.examenSPDO.reservation_service.dao.IReservationRepository;
import com.examenSPDO.reservation_service.dto.Reservation;
import com.examenSPDO.reservation_service.dto.RoomResponse;
import com.examenSPDO.reservation_service.entities.ReservationEntity;
import com.examenSPDO.reservation_service.entities.ReservationStatus;
import com.examenSPDO.reservation_service.mapping.ReservationMapper;
import com.examenSPDO.reservation_service.mid.RoomClientHttp;
import com.examenSPDO.reservation_service.service.IKafkaService;
import com.examenSPDO.reservation_service.service.IReservationService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import com.examenSPDO.reservation_service.exception.*;
import org.springframework.context.MessageSource;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "reservations")
public class ReservationService implements IReservationService {

    private final IReservationRepository iReservationRepository;
    private final ReservationMapper reservationMapper;
    private final RoomClientHttp roomClientHttp;
    private final IKafkaService iKafkaService;
    private final MessageSource messageSource;

    @Override
    @Transactional(readOnly = true)
    @Cacheable
    public List<Reservation> getReservations() {
        return iReservationRepository.findAll()
                .stream()
                .map(reservationMapper::toReservation)
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable(key="#id")
    @Transactional(readOnly = true)
    public Reservation getReservation(Long id) {
        log.info("Recherche chambre id={}", id);
        Reservation reservation= reservationMapper.toReservation(
                iReservationRepository.findById(id)
                .orElseThrow(() ->{
                    log.error(" Chambre introuvable id={}", id);
                    return new EntityNotFoundException(
                            messageSource.getMessage("reservation.notfound", new Object[]{id},
                                    Locale.getDefault()));
                    })
        );
        log.debug(" Reservation trouvée depuis Redis ou BDD : {}", reservation);
        return reservation;
    }

    @Override
    @Transactional
    public Reservation createReservation(Reservation reservation) {
        // 1. REST SYNC → room-service recuperer le room
        RoomResponse  romReserved= roomClientHttp.getRoomById(reservation.getRoomId());
        if(romReserved == null){
            throw new RequestException(
                    messageSource.getMessage("room.notfound",
                            new Object[]{reservation.getRoomId()}, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST);
        }
        if (!romReserved.getAvailable()) {
            throw new RequestException(
                    messageSource.getMessage("room.notavailable",
                            new Object[]{reservation.getRoomId()}, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST);
        }

        // 2. Calculer prix total
        long days = ChronoUnit.DAYS.between(
                reservation.getCheckIn(), reservation.getCheckOut());
        reservation.setTotalPrice(romReserved.getPrice() * days);
        reservation.setStatus(ReservationStatus.PENDING);

        // 3. Sauvegarder en BDD

        ReservationEntity reservationSaved = iReservationRepository.save(reservationMapper.fromReservation(reservation));

        if (reservationSaved == null) {
            throw new RequestException(
                    messageSource.getMessage("reservation.error",
                            new Object[]{}, Locale.getDefault()),
                    HttpStatus.BAD_REQUEST);
        }

        // 4. KAFKA ASYNC → payment-service
        iKafkaService.sendReservation(reservationMapper.toReservationKafka(reservationSaved));

        return reservationMapper.toReservation(reservationSaved) ;

    }

    @Override
    @CachePut(key = "#id")
    @Transactional
    public Reservation updateReservation(Long id, Reservation reservation) {
        return iReservationRepository.findById(id)
                .map(entity -> {
                    entity.setStatus(reservation.getStatus());
                    entity.setTotalPrice(reservation.getTotalPrice());
                    entity.setCheckIn(reservation.getCheckIn());
                    entity.setCheckOut(reservation.getCheckOut());
                    entity.setClientEmail(reservation.getClientEmail());
                    entity.setClientName(reservation.getClientName());
                    entity.setRoomId(reservation.getRoomId());
                    return reservationMapper.toReservation(iReservationRepository.save(entity));
                }).orElseThrow(
                        () -> new EntityNotFoundException(
                                messageSource.getMessage("reservation.notfound",
                                        new Object[]{id}, Locale.getDefault()))
                );
    }

    @Override
    @CacheEvict(key = "#id")
    @Transactional
    public void deleteReservation(Long id) {
        ReservationEntity reservation = iReservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage("reservation.notfound",
                                new Object[]{id}, Locale.getDefault())));

        try {
            iReservationRepository.delete(reservation);
        }catch (Exception e) {
            throw new RequestException(
                    messageSource.getMessage("reservation.errordeletion",
                            new Object[]{id}, Locale.getDefault()),
                    HttpStatus.CONFLICT);
        }
    }

    @Override
    public Page<Reservation> findAll(Pageable pageable) {
        return null;
    }
}
