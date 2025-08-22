package com.project.library_management.controller;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import com.project.library_management.service.iservice.IUserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService iUserService;

    @BeforeEach
    void setUp() {
        // Mock the service call to return a successful response
        when(iUserService.myInfor(anyString())).thenReturn(ResponseEntity.ok().build());
    }

    @Test
    void testMyInfor() throws Exception {
        mockMvc.perform(post("/user/infor")
                .param("email", "admin"))
                .andExpect(status().isOk());
    }
}
