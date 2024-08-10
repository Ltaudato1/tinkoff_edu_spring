package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final ApplicationConfig applicationConfig;

    @Bean
    public NewTopic scrapperTopic() {
        return TopicBuilder.name("${kafka.topic}")
            .partitions(1)
            .replicas(1)
            .build();
    }
}
