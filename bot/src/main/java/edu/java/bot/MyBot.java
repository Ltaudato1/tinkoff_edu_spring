package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.BotCommand;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SetMyCommands;
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

        bot.execute(
            new SetMyCommands(
                new BotCommand("start", "Начать работу с ботом"),
                new BotCommand("help", "Вывести список команд"),
                new BotCommand("list", "Вывести список отслеживаемых ссылок"),
                new BotCommand("track", "Начать отслеживать ссылку"),
                new BotCommand("untrack", "Прекратить отслеживать ссылку")
            )
        );
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
