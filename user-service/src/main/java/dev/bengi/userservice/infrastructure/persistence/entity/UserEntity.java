package dev.bengi.userservice.infrastructure.persistence.entity;

import dev.bengi.userservice.domain.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id;

    private String email;
    private String firstName;
    private String lastName;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean active;
}