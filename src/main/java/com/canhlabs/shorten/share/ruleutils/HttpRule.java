package com.canhlabs.shorten.share.ruleutils;

import lombok.extern.slf4j.Slf4j;

/**
 * Validation url String format
 */
@Slf4j
public class HttpRule implements Rule<String> {

    /**
     * check string is valid url
     *
     * @param url string to check
     *            throw exception in case is not valid
     */
    @Override
    public void execute(String url) {
        log.info("validation url:{}", url);
        // todo(canh)
        // check empty
        // check prefix, in case lack http/https, auto fill it
        // in case  suffix, raise error

    }
}
