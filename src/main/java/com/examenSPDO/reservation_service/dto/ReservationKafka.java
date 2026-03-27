package com.examenSPDO.reservation_service.dto;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

// objet envoyé dans le topic Kafka
@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ReservationKafka implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private Long roomId;
    private String clientName;
    private String clientEmail;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String status;
    private Double totalPrice;
}
