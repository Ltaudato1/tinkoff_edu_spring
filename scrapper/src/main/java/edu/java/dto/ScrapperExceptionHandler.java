package edu.java.dto;

import edu.java.dto.exceptions.ChatAlreadyRegisteredException;
import edu.java.dto.exceptions.LinkAlreadyAddedException;
import edu.java.dto.exceptions.NoSuchLinkException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ScrapperExceptionHandler {
    private static final String ERROR_CODE = "400";

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleException(Exception e) {
        // Обработка прочих исключений
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Server error",
            ERROR_CODE,
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getStackTrace()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(ChatAlreadyRegisteredException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleChatAlreadyRegisteredException(ChatAlreadyRegisteredException e) {
        // Обработка прочих исключений
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Chat has already registered",
            ERROR_CODE,
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getStackTrace()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(NoSuchLinkException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleNoSuchLinkException(NoSuchLinkException e) {
        // Обработка прочих исключений
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Link hasn't been found",
            "404",
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getStackTrace()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(LinkAlreadyAddedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleLinkAlreadyAddedException(LinkAlreadyAddedException e) {
        // Обработка прочих исключений
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Link has already added",
            ERROR_CODE,
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getStackTrace()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
