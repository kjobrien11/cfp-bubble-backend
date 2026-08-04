package com.cfpbubble.cfpbubble.dto;

public record ErrorResponse(
        String error,
        String message
) {
}
