package dev.bengi.userservice.infrastructure.persistence.repository;

import dev.bengi.userservice.infrastructure.persistence.entity.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    @Query("""
            select t from TokenEntity t inner join UserEntity u on t.user.id = u.id
            where u.id = :userId and (t.expired = false or t.revoked = false)
            """)
    List<TokenEntity> findAllValidTokensByUser(UUID userId);

    Optional<TokenEntity> findByToken(String token);
}