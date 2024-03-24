package edu.java.configuration;

import edu.java.domain.jpa.ChatsJpaDao;
import edu.java.domain.jpa.LinksJpaDao;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jpa.JpaChatService;
import edu.java.service.jpa.JpaLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    public LinkService linkService(LinksJpaDao jpaDao) {
        return new JpaLinkService(jpaDao);
    }

    @Bean
    public ChatService chatService(ChatsJpaDao jpaDao) {
        return new JpaChatService(jpaDao);
    }
}
