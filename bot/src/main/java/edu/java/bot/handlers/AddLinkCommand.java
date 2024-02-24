package edu.java.bot.handlers;

import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.net.URL;
import static edu.java.bot.parsers.LinkParser.stringToUrl;

public class AddLinkCommand implements Command {
    UserMessage userMessage;

    public AddLinkCommand(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public void execute(Long chatId, User user, String argument) {
        if (argument != null) {
            URL urlArgument = stringToUrl(argument);
            if (!user.trackedList().contains(urlArgument)) {
                user.addLink(urlArgument);
                userMessage.successfulAdd(chatId);
            } else {
                userMessage.alreadyHasThisLink(chatId);
            }
        } else {
            userMessage.invalidArgument(chatId);
        }
    }
}
