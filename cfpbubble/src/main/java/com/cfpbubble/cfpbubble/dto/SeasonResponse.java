package com.cfpbubble.cfpbubble.dto;

import java.time.LocalDateTime;

public record SeasonResponse(
        Integer year,
        LocalDateTime submissionOpen,
        LocalDateTime submissionClosed,
        String status
) {
}
