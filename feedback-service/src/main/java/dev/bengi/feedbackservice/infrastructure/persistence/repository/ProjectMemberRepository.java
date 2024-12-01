package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.domain.model.ProjectMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {
    boolean existsByProjectIdAndUserId(UUID projectId, UUID userId);
    Optional<ProjectMember> findByProjectIdAndUserId(UUID projectId, UUID userId);
    List<ProjectMember> findByProjectId(UUID projectId);
    List<ProjectMember> findByUserId(UUID userId);
    
    @Query("SELECT pm.projectId FROM ProjectMember pm WHERE pm.userId = :userId")
    List<UUID> findProjectIdsByUserId(@Param("userId") UUID userId);
} 