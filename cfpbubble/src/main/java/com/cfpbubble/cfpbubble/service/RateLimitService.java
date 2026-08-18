package com.cfpbubble.cfpbubble.service;


import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RateLimitService {

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    public boolean allowSubmission(String ipAddress) {

        Bucket bucket = buckets.computeIfAbsent(
                ipAddress,
                key -> createBucket()
        );

        return bucket.tryConsume(1);
    }

    private Bucket createBucket() {

        Bandwidth limit = Bandwidth.classic(
                10,
                Refill.greedy(10, Duration.ofHours(1))
        );

        return Bucket.builder()
                .addLimit(limit)
                .build();
    }
}
