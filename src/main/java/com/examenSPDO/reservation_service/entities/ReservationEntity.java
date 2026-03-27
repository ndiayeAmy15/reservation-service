package com.examenSPDO.reservation_service.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String clientName;
    private String clientEmail;
    private Long roomId;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private ReservationStatus status;
}
