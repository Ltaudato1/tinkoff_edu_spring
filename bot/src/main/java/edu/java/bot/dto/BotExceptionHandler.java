package edu.java.bot.dto;

import edu.java.bot.dto.exceptions.ChatNotExistsException;
import edu.java.bot.dto.exceptions.DuplicateUpdateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BotExceptionHandler {
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

    @ExceptionHandler(ChatNotExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleChatNotExistException(ChatNotExistsException e) {
        // Обработка исключения несуществующего чата
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Chat doesn't exist",
            ERROR_CODE,
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getStackTrace()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(DuplicateUpdateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiErrorResponse> handleDuplicateUpdateException(DuplicateUpdateException e) {
        // Обработка исключения повторной обработки обновления
        ApiErrorResponse errorResponse = new ApiErrorResponse(
            "Update has already processed",
            ERROR_CODE,
            e.getClass().getSimpleName(),
            e.getMessage(),
            e.getStackTrace()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
