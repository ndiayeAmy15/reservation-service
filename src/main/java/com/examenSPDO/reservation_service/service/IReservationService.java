package com.examenSPDO.reservation_service.service;

import com.examenSPDO.reservation_service.dto.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IReservationService {
    List<Reservation> getReservations();
    Reservation getReservation(Long id);

    Reservation createReservation(Reservation Reservation);
    Reservation updateReservation(Long id,Reservation Reservation);
    void deleteReservation(Long id);
    Page<Reservation> findAll(Pageable pageable);
}
