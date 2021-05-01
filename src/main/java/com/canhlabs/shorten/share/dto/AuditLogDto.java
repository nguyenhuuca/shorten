package com.canhlabs.shorten.share.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class AuditLogDto extends BaseDto {
    // track IP of client
    String ip;
    // track action from client
    String action;
    // track message info like body request
    String contentSend;
}
