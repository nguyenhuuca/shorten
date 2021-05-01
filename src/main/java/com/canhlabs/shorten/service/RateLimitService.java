package com.canhlabs.shorten.service;

import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.exception.CustomException;
import org.springframework.http.HttpStatus;

/**
 * To avoid DDOS, apply rating limit for system
 */
public interface RateLimitService {

    /**
     * only permit the some request per minute, raise error is case not valid
     *
     * @param identifier can is user or IP request
     */
    void checkLimit(String identifier);

    default void raiseError() {
        throw CustomException.builder()
                .message("Only permit " + AppConstant.props.getCountLimit() + " request/minute on each identifier")
                .status(HttpStatus.TOO_MANY_REQUESTS)
                .build();
    }

}
