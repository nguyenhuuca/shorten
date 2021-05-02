package com.canhlabs.shorten.config.ratelimit;

import com.canhlabs.shorten.share.AppConstant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@Component
public class SlidingWindowStrategy implements RateLimiter {
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
        checkLimit(identifier, AppConstant.props.getTimeLimit(), AppConstant.props.getCountLimit());
    }

    @Override
    public void checkLimit(String identifier, long timeLimit, int countLimit) {
        long current = Instant.now().toEpochMilli();
        Set<Long> timeAccessCurrentSet = cache.getIfPresent(identifier);
        if(timeAccessCurrentSet != null) {
            // remove old time access
            timeAccessCurrentSet.removeIf(item -> current - item > timeLimit);
            if(timeAccessCurrentSet.size() == countLimit) {
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
