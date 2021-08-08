package com.canhlabs.service;

import com.canhlabs.shorten.service.KeyGenerateService;
import com.canhlabs.shorten.service.impl.KeyGenerateServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KGSTest {


    KeyGenerateService kgs;

    @BeforeEach
    void setup() {
        kgs = new KeyGenerateServiceImpl();
    }

    @Test
    void testGenKey() {
        String key = kgs.generate();
        assertEquals(6, key.length());
    }


}
