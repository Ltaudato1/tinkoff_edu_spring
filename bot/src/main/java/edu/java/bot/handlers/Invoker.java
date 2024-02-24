package edu.java.bot.handlers;

import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.util.HashMap;
import java.util.Map;

public class Invoker {
    private final Map<String, Command> commandMap = new HashMap<>();
    UserMessage userMessage;

    public Invoker(UserMessage userMessage) {
        this.userMessage = userMessage;

        commandMap.put("/start", new StartCommand(userMessage));
        commandMap.put("/help", new HelpCommand(userMessage));
        commandMap.put("/list", new ListCommand(userMessage));
        commandMap.put("/track", new AddLinkCommand(userMessage));
        commandMap.put("/untrack", new RemoveLinkCommand(userMessage));
    }

    public void executeCommand(Long chatId, User user, String[] args) {
        Command command = commandMap.get(args[0]);
        String argument = (args.length == 1) ? null : args[1];

        if (command != null) {
            command.execute(chatId, user, argument);
        } else {
            userMessage.unknownCommand(chatId);
        }
    }
}
