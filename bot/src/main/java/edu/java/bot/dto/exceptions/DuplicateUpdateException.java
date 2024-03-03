package edu.java.bot.dto.exceptions;

public class DuplicateUpdateException extends RuntimeException {
    public DuplicateUpdateException(String message) {
        super(message);
    }
}
