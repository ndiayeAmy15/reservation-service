package com.examenSPDO.reservation_service.service;

import com.examenSPDO.reservation_service.dto.Reservation;
import com.examenSPDO.reservation_service.dto.ReservationKafka;

public interface IKafkaService {
    void sendReservation(ReservationKafka reservationKafka);
}
