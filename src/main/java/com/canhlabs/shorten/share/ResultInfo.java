package com.canhlabs.shorten.share;

import com.canhlabs.shorten.share.enums.ResultStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * Using to return from any controller
 * @param <T>  extends object need to return to client
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public abstract class ResultInfo<T> {
    private ResultStatus status;
    private String message;
}