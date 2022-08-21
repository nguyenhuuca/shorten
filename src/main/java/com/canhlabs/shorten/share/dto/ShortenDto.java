package com.canhlabs.shorten.share.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class ShortenDto extends BaseDto {
    @JsonIgnore
    String hash;
    @JsonIgnore
    String originUrl;
    String shortLink;
    String qrCode; // base 64
}
