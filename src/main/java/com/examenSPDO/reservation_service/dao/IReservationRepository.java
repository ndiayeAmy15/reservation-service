package com.examenSPDO.reservation_service.dao;

import com.examenSPDO.reservation_service.entities.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IReservationRepository extends JpaRepository<ReservationEntity,Long> {
    List<ReservationEntity> findByClientName(String clientName);
    List<ReservationEntity> findByRoomId(Long roomId);
}
