package com.cfpbubble.cfpbubble.exception;

import com.cfpbubble.cfpbubble.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxBubblesException.class)
    public ResponseEntity<ErrorResponse> handleMaxBubbles(
            MaxBubblesException e
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "MAX_BUBBLES_REACHED",
                        e.getMessage()
                ));
    }
}
