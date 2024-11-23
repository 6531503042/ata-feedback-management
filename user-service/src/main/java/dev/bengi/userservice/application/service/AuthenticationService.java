package dev.bengi.userservice.application.service;

import dev.bengi.userservice.application.port.input.AuthenticationUseCase;
import dev.bengi.userservice.application.port.output.UserPort;
import dev.bengi.userservice.domain.model.Role;
import dev.bengi.userservice.domain.model.User;
import dev.bengi.userservice.infrastructure.security.JwtService;
import dev.bengi.userservice.presentation.dto.request.AuthenticationRequest;
import dev.bengi.userservice.presentation.dto.request.RegisterRequest;
import dev.bengi.userservice.presentation.dto.request.RefreshTokenRequest;
import dev.bengi.userservice.presentation.dto.request.LogoutRequest;
import dev.bengi.userservice.presentation.dto.response.AuthenticationResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserPort userPort;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        logger.info("Registering new user with email: {}", request.getEmail());

        if (userPort.existsByEmail(request.getEmail())) {
            logger.error("Email already exists: {}", request.getEmail());
            throw new IllegalArgumentException("Email already exists");
        }

        var user = User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .enabled(true)
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .build();

        var savedUser = userPort.save(user);
        var accessToken = jwtService.generateToken(savedUser);
        var refreshToken = jwtService.generateRefreshToken(savedUser);

        var response = AuthenticationResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        logger.info("User registered successfully. User ID: {}", savedUser.getId());
        return response;
    }

    @Override
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        logger.info("Attempting authentication for user: {}", request.getEmail());

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = userPort.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", request.getEmail());
                    return new IllegalArgumentException("Invalid email or password");
                });

        var accessToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        var response = AuthenticationResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        logger.info("User authenticated successfully. User ID: {}", user.getId());
        return response;
    }

    @Override
    public AuthenticationResponse refreshToken(RefreshTokenRequest request) {
        logger.info("Attempting to refresh token");

        final String refreshToken = request.getRefreshToken();
        final String userEmail = jwtService.extractUsername(refreshToken);

        var user = userPort.findByEmail(userEmail)
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", userEmail);
                    return new IllegalArgumentException("Invalid refresh token");
                });

        if (!jwtService.isTokenValid(refreshToken, user)) {
            logger.error("Invalid refresh token for user: {}", userEmail);
            throw new IllegalArgumentException("Invalid refresh token");
        }

        var accessToken = jwtService.generateToken(user);

        var response = AuthenticationResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        logger.info("Token refreshed successfully for user ID: {}", user.getId());
        return response;
    }

    @Override
    public void logout(LogoutRequest request) {
        logger.info("Processing logout request");
        // Implementation of logout logic
        logger.info("User logged out successfully");
    }
}