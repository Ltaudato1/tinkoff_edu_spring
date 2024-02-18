package edu.java.bot;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Update;
import edu.java.bot.configuration.ApplicationConfig;
import edu.java.bot.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import edu.java.bot.user.UserMessage;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import static edu.java.bot.parsers.CommandParser.parseCommand;

@Component
public class MyBot {
    @Getter private final TelegramBot bot;
    Map<Long, User> userList;
    ApplicationConfig applicationConfig;

    @Autowired
    public MyBot(ApplicationConfig applicationConfig) {
        bot = new TelegramBot(applicationConfig.telegramToken());
        userList = new HashMap<>();
        this.applicationConfig = applicationConfig;
        UserMessage.setBot(bot);
    }

    public void processUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {
            long chatId = update.message().chat().id();
            String message = update.message().text();

            if (!userList.containsKey(chatId)) {
                addUser(new User(chatId, new ArrayList<>()));
            }

            try {
                parseCommand(userList.get(chatId), message);
            } catch (Exception e) {
                return;
            }
        }
    }

    public void processCallbackQuery(CallbackQuery callbackQuery) {
        String command = callbackQuery.data();

        switch (command) {

        }
    }

    public void addUser(User user) {
        userList.put(user.chatId(), user);
    }
}
