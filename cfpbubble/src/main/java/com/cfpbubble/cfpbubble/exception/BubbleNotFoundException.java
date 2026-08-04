package com.cfpbubble.cfpbubble.exception;

public class BubbleNotFoundException extends RuntimeException {
    public BubbleNotFoundException() {
        super("Bubble not found");
    }

    public BubbleNotFoundException(String message) {
        super(message);
    }
}
