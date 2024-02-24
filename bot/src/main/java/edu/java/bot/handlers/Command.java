package edu.java.bot.handlers;

import edu.java.bot.user.User;

public interface Command {
    void execute(Long chatId, User user, String argument);
}
