package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.handlers.Invoker;
import edu.java.bot.parsers.CommandParser;
import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class MyBot {
    @Getter private final TelegramBot bot;
    private final Map<Long, User> users;
    ApplicationConfig applicationConfig;
    private final CommandParser commandParser;

    @Autowired
    public MyBot(ApplicationConfig applicationConfig) {
        UserMessage userMessage = new UserMessage();
        Invoker invoker = new Invoker(userMessage);
        commandParser = new CommandParser(userMessage, invoker);

        bot = new TelegramBot(applicationConfig.telegramToken());
        users = new HashMap<>();
        this.applicationConfig = applicationConfig;
        userMessage.setBot(bot);
    }

    public void processUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            long chatId = update.message().chat().id();
            String message = update.message().text();

            if (!users.containsKey(chatId)) {
                addUser(chatId, new User(new ArrayList<>()));
            }

            try {
                commandParser.parseCommand(chatId, users.get(chatId), message);
            } catch (Exception e) {
                log.error("error while getting update");
            }
        }
    }

    public void addUser(Long chatId, User user) {
        users.put(chatId, user);
    }
}
