package com.canhlabs.shorten.service.impl;

import com.canhlabs.shorten.service.KeyGenerateService;
import com.canhlabs.shorten.share.RandomString;
import org.springframework.stereotype.Service;

@Service
public class KeyGenerateServiceImpl implements KeyGenerateService {
    private static final int KEY_LEN = 6;
    /**
     *
     * @return random string
     */
    @Override
    public String generate() {
        RandomString gen = new RandomString(KEY_LEN);
        return gen.nextString();
    }
}
