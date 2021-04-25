package com.canhlabs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties
@EnableCaching
@Slf4j
public class ShortenApp {
    public static void main(String[] args) {
        SpringApplication.run(ShortenApp.class, args);
        log.info("Starting Shorten application");

    }
    @Bean
    void registShortenEvent() {

    }

}