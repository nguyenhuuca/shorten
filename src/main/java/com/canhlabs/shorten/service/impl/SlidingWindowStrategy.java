package com.canhlabs.shorten.service.impl;

import com.canhlabs.shorten.service.RateLimitService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Service
public class SlidingWindowStrategy implements RateLimitService {
    private Cache<String, Set<Long>> cache;
    @PostConstruct
    public void init() {

       cache = CacheBuilder.newBuilder().build();
    }


    /**
     * Apply Sliding Window algorithm, takes a lot of memory
     * @param identifier can is user or IP request
     */
    @Override
    public void checkLimit(String identifier) {
        long current = Instant.now().toEpochMilli();
        Set<Long> timeAccessCurrentSet = cache.getIfPresent(identifier);
        if(timeAccessCurrentSet != null) {
            // remove old time access
            timeAccessCurrentSet.removeIf(item -> current - item > TIME_LIMIT);
            if(timeAccessCurrentSet.size() == COUNT_LIMIT) {
                raiseError();
            } else {
                timeAccessCurrentSet.add(current);
            }

        } else {
            Set<Long> timeAccessSet = new HashSet<>();
            timeAccessSet.add(current);
            cache.put(identifier, timeAccessSet);
        }


    }
}
