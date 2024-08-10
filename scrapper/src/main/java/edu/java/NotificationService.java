package edu.java;

import edu.java.configuration.ApplicationConfig;
import edu.java.dto.LinkUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final ApplicationConfig applicationConfig;
    private final ScrapperQueueProducer scrapperQueueProducer;
    private final BotClient botClient;

    public void sendNotification(LinkUpdateRequest update) {
        if (applicationConfig.useQueue()) {
            scrapperQueueProducer.send(update);
        } else {
            botClient.processUpdate(update);
        }
    }
}
