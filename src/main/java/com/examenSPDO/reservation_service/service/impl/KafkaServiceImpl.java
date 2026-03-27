package com.examenSPDO.reservation_service.service.impl;

import com.examenSPDO.reservation_service.dto.ReservationKafka;
import com.examenSPDO.reservation_service.service.IKafkaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaServiceImpl implements IKafkaService {

    private final KafkaTemplate<String, ReservationKafka> kafkaTemplate;

    @Value("${kafka.topic}")
    private String kafkaTopic;
    @Override
    public void sendReservation(ReservationKafka reservation) {
        log.info("Envoi Kafka reservation : {}", reservation);
        log.info("Reservation roomId : {}", reservation.getRoomId());
        kafkaTemplate.send(kafkaTopic,reservation);
    }
}
