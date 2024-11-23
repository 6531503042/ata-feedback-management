package dev.bengi.userservice.infrastructure.persistence.adapter;

import dev.bengi.userservice.application.port.output.UserPort;
import dev.bengi.userservice.domain.model.User;
import dev.bengi.userservice.infrastructure.persistence.entity.UserEntity;
import dev.bengi.userservice.infrastructure.persistence.mapper.UserMapper;
import dev.bengi.userservice.infrastructure.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPort {
    private final UserJpaRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = userRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}