package edu.java.bot.handlers;

import edu.java.bot.user.User;
import edu.java.bot.user.UserMessage;
import java.net.URL;
import static edu.java.bot.parsers.LinkParser.stringToUrl;

public class RemoveLinkCommand implements Command {
    UserMessage userMessage;

    public RemoveLinkCommand(UserMessage userMessage) {
        this.userMessage = userMessage;
    }

    @Override
    public void execute(Long chatId, User user, String argument) {
        if (argument != null) {
            URL urlArgument = stringToUrl(argument);
            if (user.trackedList().contains(urlArgument)) {
                user.removeLink(urlArgument);
                userMessage.successfulDelete(chatId);
            } else {
                userMessage.noSuchLink(chatId);
            }
        } else {
            userMessage.invalidArgument(chatId);
        }
    }
}
