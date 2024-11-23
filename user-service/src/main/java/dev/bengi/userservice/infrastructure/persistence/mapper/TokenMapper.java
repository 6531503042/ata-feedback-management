package dev.bengi.userservice.infrastructure.persistence.mapper;

import dev.bengi.userservice.domain.model.Token;
import dev.bengi.userservice.infrastructure.persistence.entity.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenMapper {
    private final UserMapper userMapper;

    public Token toDomain(TokenEntity entity) {
        if (entity == null)
            return null;
        return Token.builder()
                .id(entity.getId())
                .token(entity.getToken())
                .tokenType(entity.getTokenType())
                .expired(entity.isExpired())
                .revoked(entity.isRevoked())
                .user(userMapper.toDomain(entity.getUser()))
                .build();
    }

    public TokenEntity toEntity(Token domain) {
        if (domain == null)
            return null;
        return TokenEntity.builder()
                .id(domain.getId())
                .token(domain.getToken())
                .tokenType(domain.getTokenType())
                .expired(domain.isExpired())
                .revoked(domain.isRevoked())
                .user(userMapper.toEntity(domain.getUser()))
                .build();
    }
}