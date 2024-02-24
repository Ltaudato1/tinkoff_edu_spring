package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.handlers.CommandHandler;
import edu.java.bot.parsers.CommandParser;
import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyBot {
    @Getter private final TelegramBot bot;
    private final Map<Long, User> users;
    ApplicationConfig applicationConfig;
    private static final Logger LOGGER = Logger.getLogger(MyBot.class.getName());
    private final CommandParser commandParser;

    @Autowired
    public MyBot(ApplicationConfig applicationConfig) {
        UserMessage userMessage = new UserMessage();
        CommandHandler commandHandler = new CommandHandler(userMessage);
        commandParser = new CommandParser(commandHandler);

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
                LOGGER.warning("Error while sending message to user");
            }
        }
    }

    public void addUser(Long chatId, User user) {
        users.put(chatId, user);
    }
}
