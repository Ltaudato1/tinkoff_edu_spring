package edu.java.bot.handlers;

import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;

public class StartCommand implements Command {
    UserMessage userMessage;

    public StartCommand(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public void execute(Long chatId, User user, String argument) {
        userMessage.startCommand(chatId);
    }
}
