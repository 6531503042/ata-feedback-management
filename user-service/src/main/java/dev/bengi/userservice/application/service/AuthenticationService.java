package dev.bengi.userservice.application.service;

import dev.bengi.userservice.application.port.input.AuthenticationUseCase;
import dev.bengi.userservice.application.port.output.UserPort;
import dev.bengi.userservice.domain.model.Role;
import dev.bengi.userservice.domain.model.TokenType;
import dev.bengi.userservice.domain.model.User;
import dev.bengi.userservice.infrastructure.persistence.entity.TokenEntity;
import dev.bengi.userservice.infrastructure.persistence.mapper.UserMapper;
import dev.bengi.userservice.infrastructure.persistence.repository.TokenRepository;
import dev.bengi.userservice.infrastructure.security.JwtService;
import dev.bengi.userservice.presentation.dto.request.AuthenticationRequest;
import dev.bengi.userservice.presentation.dto.request.RegisterRequest;
import dev.bengi.userservice.presentation.dto.request.RefreshTokenRequest;
import dev.bengi.userservice.presentation.dto.request.LogoutRequest;
import dev.bengi.userservice.presentation.dto.response.AuthenticationResponse;
import dev.bengi.userservice.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    private final UserPort userPort;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenRepository tokenRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        if (userPort.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }

        var user = User.builder()
                .id(UUID.randomUUID())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .active(true)
                .build();

        var savedUser = userPort.save(user);
        var securityUser = new SecurityUser(savedUser);
        var accessToken = jwtService.generateToken(securityUser);
        var refreshToken = jwtService.generateRefreshToken(securityUser);

        saveUserToken(savedUser, accessToken);

        return AuthenticationResponse.builder()
                .userId(savedUser.getId())
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));

        var user = userPort.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    logger.error("User not found with email: {}", request.getEmail());
                    return new IllegalArgumentException("Invalid email or password");
                });

        // Revoke all existing tokens
        revokeAllUserTokens(user);

        var securityUser = new SecurityUser(user);
        var accessToken = jwtService.generateToken(securityUser);
        var refreshToken = jwtService.generateRefreshToken(securityUser);

        // Save both tokens
        saveUserToken(user, accessToken);
        saveUserToken(user, refreshToken);

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
        final String refreshToken = request.getRefreshToken();
        final String userEmail = jwtService.extractUsername(refreshToken);

        var user = userPort.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        var securityUser = new SecurityUser(user);
        if (!jwtService.isTokenValid(refreshToken, securityUser)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }

        var accessToken = jwtService.generateToken(securityUser);

        return AuthenticationResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public void logout(LogoutRequest request) {
        logger.info("Processing logout request");
        // Implementation of logout logic
        logger.info("User logged out successfully");
    }

    private void saveUserToken(User user, String jwtToken) {
        var userEntity = userMapper.toEntity(user);
        
        // Check if token already exists
        tokenRepository.findByToken(jwtToken)
                .ifPresent(tokenRepository::delete);
        
        var token = TokenEntity.builder()
                .id(UUID.randomUUID())  // Explicitly set UUID
                .user(userEntity)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        tokenRepository.save(token);
        logger.debug("Saved token for user: {}", user.getEmail());
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUserTokens.isEmpty()) {
            return;
        }
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
            token.setUpdatedAt(LocalDateTime.now());
        });
        tokenRepository.saveAll(validUserTokens);
        logger.debug("Revoked all existing tokens for user: {}", user.getEmail());
    }
}