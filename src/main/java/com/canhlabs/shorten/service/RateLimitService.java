package com.canhlabs.shorten.service;

import com.canhlabs.shorten.share.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * To avoid DDOS, apply rating limit for system
 */
public interface RateLimitService {
    long TIME_LIMIT = 60000; // 1 minute
    int COUNT_LIMIT = 40;

    /**
     * only permit the some request per minute, raise error is case not valid
     *
     * @param identifier can is user or IP request
     */
    void checkLimit(String identifier);

    default void raiseError() {
        throw CustomException.builder()
                .message("Only permit " + COUNT_LIMIT + " request on each identifier")
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .build();
    }

}
