package com.example.demo.websocket.components;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;

public class WebSocketRateLimiter {
	 private final Map<String, Bucket> userBuckets = new ConcurrentHashMap<>();

	    public boolean isAllowed(String username) {
	        Bucket bucket = userBuckets.computeIfAbsent(username, this::newBucket);
	        return bucket.tryConsume(1);
	    }

	    private Bucket newBucket(String userId) {
	        Bandwidth limit = Bandwidth.simple(5, Duration.ofSeconds(1));
	        return Bucket.builder().addLimit(limit).build();
	    }
}
