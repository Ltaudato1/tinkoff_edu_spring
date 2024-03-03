package edu.java.dto.exceptions;

public class LinkAlreadyAddedException extends RuntimeException {
    public LinkAlreadyAddedException(String message) {
        super(message);
    }
}
