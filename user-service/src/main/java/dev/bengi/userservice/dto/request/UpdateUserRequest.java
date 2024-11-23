package dev.bengi.userservice.dto.request;

import dev.bengi.userservice.domain.model.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    private String firstName;
    private String lastName;

    @Email(message = "Invalid email format")
    private String email;
    private String password;
    private Role role;
}