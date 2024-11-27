package dev.bengi.userservice.domain.model;

import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private UUID id;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private Role role;
    private boolean active;
}
