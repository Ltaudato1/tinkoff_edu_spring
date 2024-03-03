package edu.java.bot.dto.exceptions;

public class ChatNotExistsException extends RuntimeException {
    public ChatNotExistsException(String message) {
        super(message);
    }
}
