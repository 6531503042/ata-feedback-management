package dev.bengi.feedbackservice.domain.model;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class User {
    UUID id;
    String email;
    String firstName;
    String lastName;
    String role;
    boolean active;
} 