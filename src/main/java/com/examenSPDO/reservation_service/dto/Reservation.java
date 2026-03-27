package com.examenSPDO.reservation_service.dto;

import com.examenSPDO.reservation_service.entities.ReservationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reservation implements Serializable {
        private static final long serialVersionUID = 1L;

        private Long id;

        @NotBlank(message = "Le nom du client ne doit pas être vide")
        private String clientName;

        @NotBlank(message = "L'email du client ne doit pas être vide")
        private String clientEmail;

        @NotNull(message = "L'id de la chambre ne doit pas être null")
        private Long roomId;

        @NotNull(message = "La date d'arrivée ne doit pas être nulle")
        private LocalDate checkIn;

        @NotNull(message = "La date de départ ne doit pas être nulle")
        private LocalDate checkOut;

        private ReservationStatus status;
        private Double totalPrice;
}
