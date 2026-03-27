package com.examenSPDO.reservation_service.client;

import com.examenSPDO.reservation_service.dto.RoomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
public class RoomClient {

    private final RestTemplate restTemplate;
    private static final String ROOM_SERVICE_URL = "http://room-service:8081";

    public RoomClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // Vérifier disponibilité chambre
    public RoomResponse getRoom(Long roomId) {
        log.info("Appel room-service pour chambre id={}", roomId);
        return restTemplate.getForObject(
                ROOM_SERVICE_URL + "/rooms/" + roomId,
                RoomResponse.class
        );
    }

    // Bloquer la chambre après réservation
    public void updateAvailability(Long roomId, Boolean available) {
        log.info("Mise à jour disponibilité chambre id={}", roomId);
        RoomResponse room = getRoom(roomId);
        if (room != null) {
            room.setAvailable(available);
            restTemplate.put(
                    ROOM_SERVICE_URL + "/rooms/" + roomId,
                    room
            );
        }
    }
}