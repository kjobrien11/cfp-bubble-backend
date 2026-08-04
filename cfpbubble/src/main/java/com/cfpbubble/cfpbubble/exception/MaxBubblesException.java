package com.cfpbubble.cfpbubble.exception;

public class MaxBubblesException extends RuntimeException {

    public MaxBubblesException() {
        super("Maximum number of bubbles reached for this season");
    }

    public MaxBubblesException(String message) {
        super(message);
    }
}
