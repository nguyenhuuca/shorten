package com.canhlabs.shorten.config.ratelimit;

import com.canhlabs.shorten.share.AppConstant;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class SlidingWindowWithCounterStrategy implements RateLimiter {
    // max element inn list is 2
    // element index 0: keep the time and value of window before
    // element index 1: keep time anh counter of next window
    private Cache<String, List<CountValue>> cache;
    @PostConstruct
    public void init() {
        cache = CacheBuilder.newBuilder().build();
    }

    /**
     * Using slide window
     * @param identifier can is user or IP request
     */
    @Override
    public void checkLimit(String identifier) {
        long current = Instant.now().toEpochMilli();
        List<CountValue> values = cache.getIfPresent(identifier);
        if(values != null) {
            if(values.size() == 1) {
                CountValue countValue = new CountValue((short) 1, current);
                values.add(countValue);
            } else {
                // values size = 2
                // check to increase count for 2th element
                CountValue countValue = values.get(1);
                if(current - countValue.currentAccessTime >= AppConstant.props.getTimeLimit()) {
                    // keep the time anh counter current,
                    values.get(0).currentAccessTime = countValue.currentAccessTime;
                    values.get(0).count = countValue.count;
                    values.remove(1);
                } else {
                    // calculate the average of counter
                    double rate = getRate(current, values);
                    if(rate + 1 >= AppConstant.props.getCountLimit()) {
                        raiseError();
                    } else {
                        countValue.count++;
                    }
                }
            }

        } else {
            values = new ArrayList<>();
            CountValue countValue = new CountValue((short) 1, current);
            values.add(countValue);
            cache.put(identifier, values);
        }

    }

    private double getRate(long current, List<CountValue> values) {
        long startWindow = current - AppConstant.props.getTimeLimit();
        long distancePeriodBefore =  values.get(0).currentAccessTime - startWindow;
        if(distancePeriodBefore < 0) distancePeriodBefore = 0;
        return values.get(0).count * (distancePeriodBefore/(AppConstant.props.getTimeLimit()*1.0)) + values.get(1).count;
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
