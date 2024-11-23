package dev.bengi.userservice.infrastructure.persistence.mapper;

import dev.bengi.userservice.domain.model.User;
import dev.bengi.userservice.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserEntity entity) {
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .role(entity.getRole())
                .enabled(entity.isEnabled())
                .build();
    }

    public UserEntity toEntity(User domain) {
        return UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .password(domain.getPassword())
                .role(domain.getRole())
                .enabled(domain.isEnabled())
                .build();
    }
}