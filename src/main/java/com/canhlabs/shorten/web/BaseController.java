package com.canhlabs.shorten.web;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Common function that using by other controller
 */
@Slf4j
public class BaseController {
    public void doValidated(RequestBody body){
        log.debug(body.toString());
        // handle validation
    }
}
