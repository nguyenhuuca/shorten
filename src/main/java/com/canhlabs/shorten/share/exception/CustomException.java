package com.canhlabs.shorten.share.exception;

import com.canhlabs.shorten.share.dto.ResponseDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Builder
public class CustomException extends RuntimeException {
    private static final long serialVersionUID = -2885187154886758927L;
    private final transient Object error;
    private final String message;
    private final HttpStatus status;
    private final int subCode;
    private final String timestamp;
    private final transient Object data;

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
