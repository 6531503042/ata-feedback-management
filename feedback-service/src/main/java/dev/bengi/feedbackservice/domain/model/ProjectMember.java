package dev.bengi.feedbackservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_members")
public class ProjectMember {
    @Id
    private UUID id;
    
    @Column(name = "project_id")
    private UUID projectId;
    
    @Column(name = "user_id")
    private UUID userId;
    
    private String role;
    private boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", insertable = false, updatable = false)
    private Project project;
} 