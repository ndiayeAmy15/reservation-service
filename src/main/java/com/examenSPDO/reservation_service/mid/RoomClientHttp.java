package com.examenSPDO.reservation_service.mid;
import com.examenSPDO.reservation_service.dto.RoomResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//implémentation REST sync
@Service
@RequiredArgsConstructor
@Slf4j
public class RoomClientHttp implements IRoomClientHttp{


    private final RestTemplate restTemplate;

    @Value("${room.service.url}")
    private String roomServiceUrl;
    @Override
    public RoomResponse getRoomById(Long roomId) {
        log.info("Appel REST vers room-service pour chambre id={}", roomId);
        try{
            return restTemplate.getForObject(
                    roomServiceUrl + "/rooms/" + roomId, RoomResponse.class
            );
        } catch (Exception e) {
            log.error("Erreur appel room-service : {}", e.getMessage());
            return null;
        }

    }
}
