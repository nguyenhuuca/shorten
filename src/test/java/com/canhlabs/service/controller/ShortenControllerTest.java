package com.canhlabs.service.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ShortenControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void testShorten() throws Exception {
        mockMvc.perform(post("/v1/shorten")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"url\": \"http://abc.com\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print()).andExpect(status().isOk());
        //.andExpect(content().string(containsString("Hello, Mock")));
        //.andExpect(jsonPath("$", Matchers.hasSize(1)))
        //.andExpect(jsonPath("$[0].firstName", Matchers.is("Lokesh")));
    }
}
