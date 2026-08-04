package com.cfpbubble.cfpbubble.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PublicIdService {

    public String generateBubbleId(int year) {
        return year + UUID.randomUUID()
                        .toString()
                        .substring(0, 8)
                        .toUpperCase();
    }
}