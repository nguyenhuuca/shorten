package com.canhlabs.shorten.share.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ShortenDto extends BaseDto {
    String hash;
    String originUrl;
}
