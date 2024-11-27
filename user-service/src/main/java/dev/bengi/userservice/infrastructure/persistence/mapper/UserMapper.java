package dev.bengi.userservice.infrastructure.persistence.mapper;

import dev.bengi.userservice.domain.model.User;
import dev.bengi.userservice.domain.model.Role;
import dev.bengi.userservice.infrastructure.persistence.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User toDomain(UserEntity entity) {
        if (entity == null) return null;
        
        return User.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .password(entity.getPassword())
                .role(Role.valueOf(entity.getRole().name()))
                .active(entity.isActive())
                .build();
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) return null;
        
        return UserEntity.builder()
                .id(domain.getId())
                .email(domain.getEmail())
                .firstName(domain.getFirstName())
                .lastName(domain.getLastName())
                .password(domain.getPassword())
                .role(dev.bengi.userservice.domain.model.enums.Role.valueOf(domain.getRole().name()))
                .active(domain.isActive())
                .build();
    }
}