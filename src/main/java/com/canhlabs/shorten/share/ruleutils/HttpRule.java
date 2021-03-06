package com.canhlabs.shorten.share.ruleutils;

import com.canhlabs.shorten.share.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;

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
        UrlValidator urlValidator = new UrlValidator();
        boolean isValid = urlValidator.isValid(url);
        if (!isValid) {
            raiseError();
        }
        log.info("Executed http rule");
    }

    private void raiseError() {
        throw CustomException.builder()
                .message("Url invalid")
                .status(HttpStatus.BAD_REQUEST)
                .build();
    }
}
