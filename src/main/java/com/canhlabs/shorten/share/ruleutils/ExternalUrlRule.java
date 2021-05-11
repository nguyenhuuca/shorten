package com.canhlabs.shorten.share.ruleutils;

import com.canhlabs.shorten.share.AppConstant;
import com.canhlabs.shorten.share.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Url request shorten not same with current domain
 */
@Slf4j
public class ExternalUrlRule implements Rule<String> {

    /**
     * check url not same with current domain
     * Call after check url is valid
     *
     * @param url request by client
     */
    @Override
    public void execute(String url) {
        // check not same with domain link
        try {
            URL internalUrl = new URL(AppConstant.props.getBaseDomain());
            URL externalUrl = new URL(url);
            if (internalUrl.getHost().equals(externalUrl.getHost())) {
                raiseError("Only shorten the external URL");
            }
        } catch (MalformedURLException e) {
            raiseError(e.getMessage());
        }
        log.info("Executed validation external link");

    }

    private void raiseError(String message) {
        throw CustomException.builder()
                .message(message)
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
