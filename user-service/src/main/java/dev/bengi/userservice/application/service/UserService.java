package dev.bengi.userservice.application.service;

import dev.bengi.userservice.domain.model.Role;
import dev.bengi.userservice.domain.model.User;
import dev.bengi.userservice.application.port.input.UserUseCase;
import dev.bengi.userservice.presentation.dto.request.UpdateUserRequest;
import dev.bengi.userservice.presentation.dto.response.UserResponse;
import dev.bengi.userservice.application.port.output.UserPort;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserPort userPort;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        logger.info("Fetching all users");
        var users = userPort.findAll();
        logger.info("Found {} users", users.size());
        return users.stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponse getUserById(UUID id) {
        logger.info("Fetching user by ID: {}", id);
        return userPort.findById(id)
                .map(this::mapToUserResponse)
                .map(response -> {
                    logger.info("Found user: {}", response);
                    return response;
                })
                .orElseThrow(() -> {
                    logger.error("User not found with ID: {}", id);
                    return new EntityNotFoundException("User not found");
                });
    }

    @Override
    public UserResponse getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        logger.info("Fetching current user with email: {}", email);
        return userPort.findByEmail(email)
                .map(this::mapToUserResponse)
                .map(response -> {
                    logger.info("Found current user: {}", response);
                    return response;
                })
                .orElseThrow(() -> {
                    logger.error("Current user not found with email: {}", email);
                    return new EntityNotFoundException("User not found");
                });
    }

    @Override
    @Transactional
    public UserResponse updateUser(UUID id, UpdateUserRequest request) {
        var user = userPort.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        var updatedUser = User.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(request.getFirstName() != null ? request.getFirstName() : user.getFirstName())
                .lastName(request.getLastName() != null ? request.getLastName() : user.getLastName())
                .password(request.getPassword() != null ? 
                        passwordEncoder.encode(request.getPassword()) : 
                        user.getPassword())
                .role(request.getRole() != null ? request.getRole() : user.getRole())
                .active(user.isActive())
                .build();

        var savedUser = userPort.save(updatedUser);
        return mapToUserResponse(savedUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userPort.findByEmail(currentUserEmail)
                .orElseThrow(() -> new EntityNotFoundException("Current user not found"));

        if (!currentUser.getRole().equals(Role.ADMIN) && !currentUser.getId().equals(id)) {
            throw new AccessDeniedException("You don't have permission to delete this user");
        }

        userPort.deleteById(id);
    }

    private UserResponse mapToUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().getValue())
                .active(user.isActive())
                .build();
    }
}