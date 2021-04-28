package com.canhlabs.shorten.share.exception;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.FieldError;

/**
 * @author canh
 *
 */
@Getter
public class FieldValidationException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5160806479050132174L;

    private final FieldError fieldError;

    public FieldValidationException(String field, String defaultMessage) {
        super(defaultMessage);
        fieldError = new FieldError(StringUtils.EMPTY, field, defaultMessage);
    }

    public FieldValidationException(String objectName, String field, String defaultMessage) {
        super(defaultMessage);
        fieldError = new FieldError(objectName, field, defaultMessage);
    }

}
