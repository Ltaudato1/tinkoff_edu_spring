package edu.java.bot.parsers;

import edu.java.bot.user.User;
import static edu.java.bot.handlers.CommandHandler.handleCommand;
import static edu.java.bot.handlers.UserState.ADD_LINK;
import static edu.java.bot.handlers.UserState.GET_LIST;
import static edu.java.bot.handlers.UserState.HELP;
import static edu.java.bot.handlers.UserState.REMOVE_LINK;
import static edu.java.bot.handlers.UserState.START;
import static edu.java.bot.parsers.LinkParser.stringToUrl;
import static edu.java.bot.user.UserMessage.invalidArgument;
import static edu.java.bot.user.UserMessage.unknownCommand;

public class CommandParser {
    public static void parseCommand(User user, String command) throws Exception {
        String[] args = command.split("\\s+");
        if (args.length > 2) {
            invalidArgument(user.chatId());
        } else {
            switch (args[0]) {
                case "/start" -> handleCommand(user, START, null);
                case "/track" -> {
                    if (args.length != 2) {
                        invalidArgument(user.chatId());
                    } else {
                        handleCommand(user, ADD_LINK, stringToUrl(args[1]));
                    }
                }
                case "/untrack" -> {
                    if (args.length != 2) {
                        invalidArgument(user.chatId());
                    } else {
                        handleCommand(user, REMOVE_LINK, stringToUrl(args[1]));
                    }
                }
                case "/help" -> handleCommand(user, HELP, null);
                case "/list" -> handleCommand(user, GET_LIST, null);
                default -> unknownCommand(user.chatId());
            }
        }
    }
}
