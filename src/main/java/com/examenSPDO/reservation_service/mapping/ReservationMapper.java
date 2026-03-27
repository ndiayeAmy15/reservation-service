package com.examenSPDO.reservation_service.mapping;
import com.examenSPDO.reservation_service.dto.Reservation;
import com.examenSPDO.reservation_service.dto.ReservationKafka;
import com.examenSPDO.reservation_service.entities.ReservationEntity;
import org.mapstruct.Mapper;

@Mapper
public interface ReservationMapper {
    Reservation toReservation(ReservationEntity reservationEntity);
    ReservationEntity fromReservation(Reservation reservation);
    ReservationKafka toReservationKafka(ReservationEntity entity);
}
