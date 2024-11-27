package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.infrastructure.persistence.entity.ProjectMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProjectMemberRepository extends JpaRepository<ProjectMemberEntity, UUID> {
    List<ProjectMemberEntity> findByProjectId(UUID projectId);
    List<ProjectMemberEntity> findByUserId(UUID userId);
    boolean existsByProjectIdAndUserId(UUID projectId, UUID userId);
    
    @Query("SELECT pm.projectId FROM ProjectMemberEntity pm WHERE pm.userId = :userId")
    List<UUID> findProjectIdsByUserId(@Param("userId") UUID userId);
} 