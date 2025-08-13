package com.example.jwtapi.service;

import com.example.jwtapi.model.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> users;

    @PostConstruct
    public void init() throws IOException {
        InputStream is = getClass().getClassLoader().getResourceAsStream("users.json");
        users = objectMapper.readValue(is, new TypeReference<List<User>>() {});
    }

    public Optional<User> findByUsername(String username) {
        return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
    }
}
