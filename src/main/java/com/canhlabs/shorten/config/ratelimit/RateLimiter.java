package com.canhlabs.shorten.config.ratelimit;

import com.canhlabs.shorten.share.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * To avoid DDOS, apply rating limit for system
 */
public interface RateLimiter {

    /**
     * only permit the some request per minute, raise error is case not valid
     *
     * @param identifier can is user or IP request
     */
    void checkLimit(String identifier);

    /**
     * using to raise the limit request
     * @param identifier can is user or IP request
     * @param timeLimit time(millisecond) to cal the number of request
     * @param countLimit number of request on time limit
     */
    void checkLimit(String identifier,long timeLimit,int countLimit);

    default void raiseError(int countLimit) {
        throw CustomException.builder()
                .message("Only permit " + countLimit + " request/minute on each identifier")
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .build();
    }

}
