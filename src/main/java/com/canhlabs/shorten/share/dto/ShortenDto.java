package com.canhlabs.shorten.share.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShortenDto {
    String hash;
    String originUrl;
}
