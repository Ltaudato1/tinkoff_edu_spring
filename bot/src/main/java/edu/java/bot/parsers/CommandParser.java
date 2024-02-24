package edu.java.bot.parsers;

import edu.java.bot.handlers.Invoker;
import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;

public class CommandParser {
    UserMessage userMessage;
    Invoker invoker;

    public CommandParser(UserMessage userMessage, Invoker invoker) {
        this.userMessage = userMessage;
        this.invoker = invoker;
    }

    public void parseCommand(Long chatId, User user, String command) {
        String[] args = command.split("\\s+");
        if (args.length > 2) {
            userMessage.invalidArgument(chatId);
        } else {
            invoker.executeCommand(chatId, user, args);
        }
    }
}
