package com.canhlabs.shorten.config.aop;

import com.canhlabs.shorten.config.ratelimit.RateLimiter;
import com.canhlabs.shorten.config.ratelimit.SlidingWindowWithCounterStrategy;
import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Using to log info before and after execute function with AuditSys annotation
 */
@Aspect
@Component
@Slf4j
public class RatingLimit {
    RateLimiter limit;

    @Autowired
    public void injectLimitStrategy(SlidingWindowWithCounterStrategy limit) {
        this.limit = limit;
    }

    /**
     * Method will add log statement of audit of the methods of class in call which
     * are annotated with @Auditable
     */
    @Before("@annotation(rateLimitAble)")
    public void processRatingLimit(RateLimitAble rateLimitAble) {
        long timeLimit = rateLimitAble.timeLimit();
        short countLimit = rateLimitAble.countLimit();
        if (timeLimit <= 0) {
            timeLimit = AppConstant.props.getTimeLimit();
        }
        if (countLimit <= 0) {
            countLimit = AppConstant.props.getCountLimit();
        }

        limit.checkLimit(AppUtils.getClientIP(), timeLimit, countLimit);
    }
}
