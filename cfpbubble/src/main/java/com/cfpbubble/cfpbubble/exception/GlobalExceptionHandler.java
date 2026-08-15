package com.cfpbubble.cfpbubble.exception;

import com.cfpbubble.cfpbubble.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxBubblesException.class)
    public ResponseEntity<ErrorResponse> handleMaxBubbles(MaxBubblesException e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(new ErrorResponse(
                        "MAX_BUBBLES_REACHED",
                        e.getMessage(), ""
                ));
    }

    @ExceptionHandler(BubbleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleBubbleNotFound(BubbleNotFoundException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "BUBBLE_NOT_FOUND",
                        e.getMessage(), ""
                ));
    }

    @ExceptionHandler(SeasonStartedException.class)
    public ResponseEntity<ErrorResponse> handleBubbleNotFound(SeasonStartedException e){
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(
                        "SEASON_STARTED",
                        e.getMessage(), ""
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> handleValidationException(
            MethodArgumentNotValidException ex) {

        List<ErrorResponse> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> new ErrorResponse(
                        "Validation Error",
                        error.getDefaultMessage(),
                        error.getField()
                ))
                .toList();

        return ResponseEntity
                .badRequest()
                .body(errors);
    }
}
