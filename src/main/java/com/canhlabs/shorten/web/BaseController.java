package com.canhlabs.shorten.web;

import com.canhlabs.shorten.config.ratelimit.RateLimiter;
import com.canhlabs.shorten.config.ratelimit.SlidingWindowWithCounterStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Common function that using by other controller
 */
@Slf4j
@Component
public class BaseController {
    RateLimiter limit;
    @Autowired
    public void injectLimitStrategy(SlidingWindowWithCounterStrategy limit) {
        this.limit = limit;
    }

}
