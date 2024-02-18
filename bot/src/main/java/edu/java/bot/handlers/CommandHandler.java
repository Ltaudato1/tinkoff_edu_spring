package edu.java.bot.handlers;

import edu.java.bot.user.User;
import java.net.URL;
import static edu.java.bot.user.UserMessage.alreadyHasThisLink;
import static edu.java.bot.user.UserMessage.helpCommand;
import static edu.java.bot.user.UserMessage.invalidArgument;
import static edu.java.bot.user.UserMessage.listCommand;
import static edu.java.bot.user.UserMessage.noSuchLink;
import static edu.java.bot.user.UserMessage.startCommand;
import static edu.java.bot.user.UserMessage.successfulAdd;
import static edu.java.bot.user.UserMessage.successfulDelete;
import static edu.java.bot.user.UserMessage.unknownCommand;

public class CommandHandler {
    private CommandHandler() {

    }

    public static void handleCommand(User user, UserState currentState, Object argument) throws Exception {
        switch (currentState) {
            case HELP -> helpCommand(user.chatId());
            case START -> startCommand(user.chatId());
            case GET_LIST -> listCommand(user.chatId(), user.trackedList());
            case ADD_LINK -> {
                if (argument instanceof URL) {
                    if (!user.trackedList().contains(argument)) {
                        user.addLink((URL) argument);
                        successfulAdd(user.chatId());
                    } else {
                        alreadyHasThisLink(user.chatId());
                    }
                } else {
                    invalidArgument(user.chatId());
                }
            }
            case REMOVE_LINK -> {
                if (argument instanceof URL) {
                    if (user.trackedList().contains(argument)) {
                        user.removeLink((URL) argument);
                        successfulDelete(user.chatId());
                    } else {
                        noSuchLink(user.chatId());
                    }
                } else {
                    invalidArgument(user.chatId());
                }
            }
            default -> unknownCommand(user.chatId());
        }
    }
}
