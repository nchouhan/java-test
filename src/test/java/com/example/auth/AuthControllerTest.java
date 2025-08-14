package com.example.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void loginSuccess() throws Exception {
        String requestBody = "{\"username\": \"testuser\", \"password\": \"password123\"}";
        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    public void loginFailure() throws Exception {
        String requestBody = "{\"username\": \"wrong\", \"password\": \"invalid\"}";
        
        mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void validateTokenSuccess() throws Exception {
        // First login to get token
        String requestBody = "{\"username\": \"testuser\", \"password\": \"password123\"}";
        String response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = response.split(":")[1].replace("\"", "").replace("}", "").trim();

        mockMvc.perform(get("/auth/validate")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void validateTokenFailure() throws Exception {
        mockMvc.perform(get("/auth/validate")
                .header("Authorization", "Bearer invalid-token"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void validateTokenMissingHeader() throws Exception {
        mockMvc.perform(get("/auth/validate"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void getUsersSuccess() throws Exception {
        // First login to get token
        String requestBody = "{\"username\": \"testuser\", \"password\": \"password123\"}";
        String response = mockMvc.perform(post("/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
                .andReturn()
                .getResponse()
                .getContentAsString();

        String token = response.split(":")[1].replace("\"", "").replace("}", "").trim();

        mockMvc.perform(get("/auth/users")
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").exists())
                .andExpect(jsonPath("$[0].password").exists());
    }

    @Test
    public void getUsersUnauthorized() throws Exception {
        mockMvc.perform(get("/auth/users"))
                .andExpect(status().isUnauthorized());
    }
}