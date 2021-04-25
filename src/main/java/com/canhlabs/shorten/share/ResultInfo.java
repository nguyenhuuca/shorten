package com.canhlabs.shorten.share;

import com.canhlabs.shorten.share.enums.ResultStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Using to return from any controller
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ResultInfo {
    private ResultStatus status;
    private String message;
}