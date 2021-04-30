package com.canhlabs.shorten.service.impl;

import com.canhlabs.shorten.service.RateLimitService;
import com.canhlabs.shorten.share.AppConstant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Service
public class FixedWindowStrategy implements RateLimitService {
    private Cache<String, CountValue> cache;
    @PostConstruct
    public void init() {

       cache = CacheBuilder.newBuilder().build();
    }


    /**
     * Apply Fix Window algorithm, takes a little of memory
     * @param identifier can is user or IP request
     */
    @Override
    public void checkLimit(String identifier) {
        long current = Instant.now().toEpochMilli();
        CountValue beforeVal = cache.getIfPresent(identifier);
        if(beforeVal != null) {
            // by minute
            if(current - beforeVal.currentAccessTime >= AppConstant.props.getTimeLimit()) {
                beforeVal.count = 1;
                beforeVal.currentAccessTime = current;
            } else {
                if (beforeVal.count >= AppConstant.props.getCountLimit()) {
                    raiseError();
                } else {
                    beforeVal.count++;
                }
            }

        } else {
            CountValue newCountVal = new CountValue();
            newCountVal.count = 1;
            newCountVal.currentAccessTime = current;
            cache.put(identifier, newCountVal);
        }
    }

    static class CountValue {
        short count;
        long currentAccessTime;
    }
}
