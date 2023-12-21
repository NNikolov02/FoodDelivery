package com.example.fooddelivery.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindById1() throws Exception {

        // Create a sample Customer
        mockMvc.perform(
                        get("http://localhost:8084//delivery/customer/cart" )
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(httpBasic("Ivan55", "nikola02"))


                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.steaks[0].name").value("Ribeye-Steak"));

        //.andExpect(jsonPath("$.username1").value("Ivan2001"));


    }
}
