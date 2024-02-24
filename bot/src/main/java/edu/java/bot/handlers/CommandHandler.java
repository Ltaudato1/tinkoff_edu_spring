package edu.java.bot.handlers;

import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.net.URL;

public class CommandHandler {
    UserMessage userMessage;

    public CommandHandler(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    public void handleCommand(Long chatId, User user, UserState currentState, Object argument) {
        switch (currentState) {
            case HELP -> userMessage.helpCommand(chatId);
            case START -> userMessage.startCommand(chatId);
            case GET_LIST -> userMessage.listCommand(chatId, user.trackedList());
            case ADD_LINK -> {
                if (argument instanceof URL) {
                    if (!user.trackedList().contains(argument)) {
                        user.addLink((URL) argument);
                        userMessage.successfulAdd(chatId);
                    } else {
                        userMessage.alreadyHasThisLink(chatId);
                    }
                } else {
                    userMessage.invalidArgument(chatId);
                }
            }
            case REMOVE_LINK -> {
                if (argument instanceof URL) {
                    if (user.trackedList().contains(argument)) {
                        user.removeLink((URL) argument);
                        userMessage.successfulDelete(chatId);
                    } else {
                        userMessage.noSuchLink(chatId);
                    }
                } else {
                    userMessage.invalidArgument(chatId);
                }
            }
            case INVALID_ARGS -> userMessage.invalidArgument(chatId);
            default -> userMessage.unknownCommand(chatId);
        }
    }
}
