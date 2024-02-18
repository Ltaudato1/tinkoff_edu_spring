package edu.java.bot.parsers;

import edu.java.bot.handlers.CommandHandler;
import edu.java.bot.user.User;
import static edu.java.bot.handlers.UserState.ADD_LINK;
import static edu.java.bot.handlers.UserState.GET_LIST;
import static edu.java.bot.handlers.UserState.HELP;
import static edu.java.bot.handlers.UserState.INVALID_ARGS;
import static edu.java.bot.handlers.UserState.REMOVE_LINK;
import static edu.java.bot.handlers.UserState.START;
import static edu.java.bot.handlers.UserState.UNKNOWN_COMMAND;
import static edu.java.bot.parsers.LinkParser.stringToUrl;

public class CommandParser {
    CommandHandler commandHandler;

    public CommandParser(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public void parseCommand(User user, String command) throws Exception {
        String[] args = command.split("\\s+");
        if (args.length > 2) {
            commandHandler.handleCommand(user, INVALID_ARGS, null);
        } else {
            switch (args[0]) {
                case "/start" -> commandHandler.handleCommand(user, START, null);
                case "/track" -> {
                    if (args.length != 2) {
                        commandHandler.handleCommand(user, INVALID_ARGS, null);
                    } else {
                        commandHandler.handleCommand(user, ADD_LINK, stringToUrl(args[1]));
                    }
                }
                case "/untrack" -> {
                    if (args.length != 2) {
                        commandHandler.handleCommand(user, INVALID_ARGS, null);
                    } else {
                        commandHandler.handleCommand(user, REMOVE_LINK, stringToUrl(args[1]));
                    }
                }
                case "/help" -> commandHandler.handleCommand(user, HELP, null);
                case "/list" -> commandHandler.handleCommand(user, GET_LIST, null);
                default -> commandHandler.handleCommand(user, UNKNOWN_COMMAND, null);
            }
        }
    }
}
