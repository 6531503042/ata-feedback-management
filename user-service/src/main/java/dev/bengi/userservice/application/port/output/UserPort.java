package dev.bengi.userservice.application.port.output;

import dev.bengi.userservice.domain.model.User;
import java.util.Optional;

public interface UserPort {
    User save(User user);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}