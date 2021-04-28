package com.canhlabs.shorten.share.exception;

import com.canhlabs.shorten.share.dto.ResponseDto;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -2885187154886758927L;
    private Object error;
    private final String message;
    private final HttpStatus status;
    private int subCode;
    private String timestamp;
    private Object data;

    public CustomException(String message, HttpStatus status, int errorCode) {
        this(message, status);
        this.error = status.getReasonPhrase();
        this.subCode = errorCode;
        this.timestamp = LocalDateTime.now().toString();
    }

    public CustomException(String message, HttpStatus status, Object data) {
        this(message, status);
        this.error = status.getReasonPhrase();
        this.data = data;
        this.timestamp = LocalDateTime.now().toString();
    }

    public CustomException(String message, HttpStatus status) {
        this.error = status.getReasonPhrase();
        this.message = message;
        this.status = status;
        this.timestamp = LocalDateTime.now().toString();
    }

    public CustomException(HttpStatus status, Error apiError) {
        this(apiError.getMessage(), status);
        this.error = apiError;
        this.timestamp = LocalDateTime.now().toString();
        this.subCode = apiError.getSubCode();
    }

    public ResponseDto buildErrorMessage() {
        return ResponseDto.builder()
                .message(this.message)
                .error(this.error)
                .status(this.status.value())
                .subCode(this.subCode)
                .data(this.data)
                .timestamp(LocalDateTime.now().toString())
                .build();
    }
}
