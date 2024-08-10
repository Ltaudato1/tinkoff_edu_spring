package edu.java.bot;

import edu.java.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkUpdateListener {

    private final NotificationService notificationService;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @KafkaListener(topics = "${app.scrapper-topic.name}")
    public void listen(LinkUpdateRequest update) {
        notificationService.handleNotification(update);
    }

    @Bean
    public KafkaListenerErrorHandler customErrorHandler() {
        return (message, exception) -> {
            kafkaTemplate.send("scrapper-topic_dlq", message.getPayload().toString());
            return null;
        };
    }
}
