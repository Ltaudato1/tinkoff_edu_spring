package edu.java.bot;

import edu.java.bot.configuration.ApplicationConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationConfig.class)
@ComponentScan("edu.java.bot")
public class BotApplication {
    public static void main(String[] args) {
        SpringApplication.run(BotApplication.class, args);

        BotRunner botRunner = SpringApplication
            .run(BotApplication.class, args)
            .getBean(BotRunner.class);
        botRunner.startBot();
    }
}
