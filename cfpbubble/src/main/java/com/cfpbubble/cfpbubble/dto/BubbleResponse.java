package com.cfpbubble.cfpbubble.dto;


import java.time.LocalDateTime;
import java.util.List;

public record BubbleResponse(
        String publicId,
        String name,
        Integer season,
        LocalDateTime submissionTime,
        List<TeamResponse> teams,
        Integer wins,
        Integer losses
) {
}
