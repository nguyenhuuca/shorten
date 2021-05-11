package com.canhlabs.shorten.share;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Using to load all the properties when start application
 * Can autowire this class in class that register to Spring Application Context
 */
@Getter
@Configuration
@ConfigurationProperties("app")

public class AppProperties {

    // Key object for AES encryption, include secret key and vector key
    private Aes aes = new Aes();
    // Domain run Application
    private String baseDomain;
    private String endpoint;
    private String prefixRedirect;
    private Long timeLimit;
    private Short countLimit;
    private String errorPage;


    public void setAes(Aes aes) {
        if (StringUtils.isEmpty(this.aes.getSecretKey())) {
            this.aes = aes;
        }

    }


    public void setBaseDomain(String baseDomain) {
        if (StringUtils.isEmpty(this.baseDomain)) {
            this.baseDomain = baseDomain;
            return;
        }
        raiseError();
    }


    public void setEndpoint(String endpoint) {
        if (StringUtils.isEmpty(this.endpoint)) {
            this.endpoint = endpoint;
            return;
        }
        raiseError();
    }

    public void setPrefixRedirect(String prefixRedirect) {
        if (StringUtils.isEmpty(this.prefixRedirect)) {
            this.prefixRedirect = prefixRedirect;
            return;
        }
        raiseError();

    }

    public void setTimeLimit(Long timeLimit) {
        if (this.timeLimit == null) {
            this.timeLimit = timeLimit;
            return;
        }
        raiseError();

    }

    public void setCountLimit(Short countLimit) {
        if(this.countLimit == null) {
            this.countLimit = countLimit;
            return;
        }
        raiseError();
    }

    public void setErrorPage(String errorPage) {
        if(this.errorPage == null) {
            this.errorPage = errorPage;
            return;
        }
        raiseError();
    }

    @Getter
    public class Aes {
        private String secretKey;
        private String vectorKey;

        public void setSecretKey(String secretKey) {
            if (StringUtils.isEmpty(this.secretKey)) {
                this.secretKey = secretKey;
                return;
            }
            raiseError();

        }

        public void setVectorKey(String vectorKey) {
            if (StringUtils.isEmpty(this.vectorKey)) {
                this.vectorKey = vectorKey;
                return;
            }
            raiseError();

        }
    }

    /**
     * Raise error in case the field it set value again
     */
    private void raiseError() {
        throw new IllegalStateException("setField was already called");
    }
}

