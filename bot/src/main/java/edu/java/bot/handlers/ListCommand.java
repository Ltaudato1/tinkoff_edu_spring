package edu.java.bot.handlers;

import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;

public class ListCommand implements Command {
    UserMessage userMessage;

    public ListCommand(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public void execute(Long chatId, User user, String argument) {
        userMessage.listCommand(chatId, user.trackedList());
    }
}
