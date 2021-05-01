package com.canhlabs.shorten.share.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
public class ResponseDto implements Serializable {
    private static final long serialVersionUID = -6448620768602438445L;
    private transient Object error;
    private String message;
    private int status;
    private int subCode;
    private String timestamp;
    private transient Object data;
}
