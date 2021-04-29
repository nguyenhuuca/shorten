package com.canhlabs.shorten.share.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuditLogDto {
    // track IP of client
    String ip;
    // track action from client
    String action;
    // track message info like body request
    String contentSend;
}
