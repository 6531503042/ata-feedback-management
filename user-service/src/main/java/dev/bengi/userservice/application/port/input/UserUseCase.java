package dev.bengi.userservice.application.port.input;

import dev.bengi.userservice.presentation.dto.request.UpdateUserRequest;
import dev.bengi.userservice.presentation.dto.response.UserResponse;
import java.util.List;
import java.util.UUID;

public interface UserUseCase {
    List<UserResponse> getAllUsers();

    UserResponse getUserById(UUID id);

    UserResponse getCurrentUser();

    UserResponse updateUser(UUID id, UpdateUserRequest request);

    void deleteUser(UUID id);
}