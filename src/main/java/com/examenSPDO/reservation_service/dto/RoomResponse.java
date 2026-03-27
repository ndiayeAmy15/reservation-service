package com.examenSPDO.reservation_service.dto;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class RoomResponse implements Serializable {
    private Long id;
    private String roomNumber;
    private String type;
    private Double price;
    private Boolean available;
}
