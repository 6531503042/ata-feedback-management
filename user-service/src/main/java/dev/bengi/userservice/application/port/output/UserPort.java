package dev.bengi.userservice.application.port.output;

import dev.bengi.userservice.domain.model.User;
import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserPort {
    User save(User user);

    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);

    List<User> findAll();

    void deleteById(UUID id);
    boolean existsByEmail(String email);
}