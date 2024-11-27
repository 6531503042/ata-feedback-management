package dev.bengi.feedbackservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_members")
public class ProjectMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "project_id", nullable = false)
    private UUID projectId;
    
    @Column(name = "user_id", nullable = false)
    private UUID userId;
    
    @Column(name = "role")
    private String role;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
} 