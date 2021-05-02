package com.canhlabs.shorten.config.ratelimit;

import com.canhlabs.shorten.share.AppConstant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;

@Component
public class FixedWindowStrategy implements RateLimiter {
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
        checkLimit(identifier, AppConstant.props.getTimeLimit(), AppConstant.props.getCountLimit());

    }

    @Override
    public void checkLimit(String identifier, long timeLimit, int countLimit) {
        long current = Instant.now().toEpochMilli();
        CountValue beforeVal = cache.getIfPresent(identifier);
        if(beforeVal != null) {
            // by minute
            if(current - beforeVal.currentAccessTime >= timeLimit) {
                beforeVal.count = 1;
                beforeVal.currentAccessTime = current;
            } else {
                if (beforeVal.count >= countLimit) {
                    raiseError();
                } else {
                    beforeVal.count++;
                }
            }

        } else {
            CountValue newCountVal = new CountValue((short) 1, current);
            cache.put(identifier, newCountVal);
        }

    }


    static class CountValue {
        short count;
        long currentAccessTime;
        CountValue(short count, long currentTime) {
            this.count = count;
            this.currentAccessTime = currentTime;
        }
    }
}
