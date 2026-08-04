package com.cfpbubble.cfpbubble.dto;


public record TeamResponse(
        Integer espnId,
        String schoolName,
        String abbreviation,
        String conferenceName,
        String logoUrl
) {
}
