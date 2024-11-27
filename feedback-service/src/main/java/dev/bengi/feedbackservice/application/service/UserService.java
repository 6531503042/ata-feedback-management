package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.infrastructure.client.UserServiceClient;
import dev.bengi.feedbackservice.infrastructure.client.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserServiceClient userServiceClient;

    public UserResponse getUserById(UUID id) {
        return userServiceClient.getUserById(id);
    }
} 