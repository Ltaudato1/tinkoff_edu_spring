package edu.java.bot;

import edu.java.bot.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {


    BotController botController;

    @KafkaListener(topics = "${app.scrapper-topic.name}")
    public void handleNotification(LinkUpdateRequest update) {
        botController.processUpdate(update);
    }
}
