package com.canhlabs.shorten.share.ruleutils;

import lombok.extern.slf4j.Slf4j;

/**
 * Url request shorten not same with current domain
 */
@Slf4j
public class ExternalUrlRule implements Rule<String> {
    /**
     * check url not same with current domain
     * Call after check url is valid
     * @param url request by client
     */
    @Override
    public void execute(String url) {
        log.info("Validation external link: {}", url);
        // check not same with domain link

    }
}
