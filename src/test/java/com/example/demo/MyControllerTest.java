package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MyControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void helloReturnsMessage() throws Exception {
        mvc.perform(get("/api/hello"))
           .andExpect(status().isOk())
           .andExpect(content().string("Hello from DevOps Pipeline - v1"));
    }

    @Test
    void squareReturnsCorrectValue() throws Exception {
        mvc.perform(get("/api/square?n=5"))
           .andExpect(status().isOk())
           .andExpect(content().string("25"));
    }

    @Test 
    void homeReturnsMessage() throws Exception {
        mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string("CI/CD demo app running on AKS"));
    }
}