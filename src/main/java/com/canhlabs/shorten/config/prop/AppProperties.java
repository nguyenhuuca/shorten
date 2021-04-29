package com.canhlabs.shorten.config.prop;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app")
public class AppProperties {

    private Aes aes = new Aes();
    private String baseDomain;
    private String endpoint;
    private String prefixRedirect;
    @Getter
    @Setter
    public static class Aes {
        private String secretKey;
        private String vectorKey;
    }
}

