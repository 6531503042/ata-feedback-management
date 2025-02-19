package dev.bengi.userservice.application.port.input;

import dev.bengi.userservice.presentation.dto.request.AuthenticationRequest;
import dev.bengi.userservice.presentation.dto.request.RegisterRequest;
import dev.bengi.userservice.presentation.dto.request.RefreshTokenRequest;
import dev.bengi.userservice.presentation.dto.request.LogoutRequest;
import dev.bengi.userservice.presentation.dto.response.AuthenticationResponse;

public interface AuthenticationUseCase {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    AuthenticationResponse refreshToken(RefreshTokenRequest request);

    void logout(LogoutRequest request);
}