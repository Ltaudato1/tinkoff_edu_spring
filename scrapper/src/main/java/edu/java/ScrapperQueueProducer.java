package edu.java;

import edu.java.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScrapperQueueProducer {

    private final KafkaTemplate<String, LinkUpdateRequest> kafkaTemplate;
    private final KafkaConfiguration kafkaConfiguration;

    public void send(LinkUpdateRequest update) {
        kafkaTemplate.send(kafkaConfiguration.getTopic(), update);
    }
}
