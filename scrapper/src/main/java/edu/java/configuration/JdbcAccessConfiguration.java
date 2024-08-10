package edu.java.configuration;

import edu.java.domain.jdbc.ChatsJdbcDao;
import edu.java.domain.jdbc.LinksJdbcDao;
import edu.java.service.ChatService;
import edu.java.service.LinkService;
import edu.java.service.jdbc.JdbcChatService;
import edu.java.service.jdbc.JdbcLinkService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(LinksJdbcDao jdbcDao) {
        return new JdbcLinkService(jdbcDao);
    }

    @Bean
    public ChatService chatService(ChatsJdbcDao jdbcDao) {
        return new JdbcChatService(jdbcDao);
    }
}
