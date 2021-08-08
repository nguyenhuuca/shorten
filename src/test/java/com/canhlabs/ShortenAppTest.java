package com.canhlabs;


import com.canhlabs.shorten.service.ShortenService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
 class ShortenAppTest {

    @Autowired
    ShortenService shortenService;

    @Test
     void contextLoads() {
        Assertions.assertThat(shortenService).isNot(null);
    }

}