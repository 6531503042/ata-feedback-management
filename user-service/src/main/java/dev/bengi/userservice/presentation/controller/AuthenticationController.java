package dev.bengi.userservice.presentation.controller;

import dev.bengi.userservice.presentation.dto.request.AuthenticationRequest;
import dev.bengi.userservice.presentation.dto.request.LogoutRequest;
import dev.bengi.userservice.presentation.dto.request.RefreshTokenRequest;
import dev.bengi.userservice.presentation.dto.request.RegisterRequest;
import dev.bengi.userservice.presentation.dto.response.AuthenticationResponse;
import dev.bengi.userservice.application.port.input.AuthenticationUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationUseCase authenticationUseCase;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody @Valid RegisterRequest request) {
        return ResponseEntity.ok(authenticationUseCase.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request) {
        return ResponseEntity.ok(authenticationUseCase.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponse> refreshToken(
            @RequestBody @Valid RefreshTokenRequest request) {
        return ResponseEntity.ok(authenticationUseCase.refreshToken(request));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody @Valid LogoutRequest request) {
        authenticationUseCase.logout(request);
        return ResponseEntity.ok().build();
    }
}