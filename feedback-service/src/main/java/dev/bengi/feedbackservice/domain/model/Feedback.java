package dev.bengi.feedbackservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;

@Entity
@Table(name = "feedbacks")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Feedback {
    @Id
    private UUID id;
    
    @Column(name = "project_id")
    private UUID projectId;
    
    @Column(name = "user_id")
    private UUID userId;
    
    @Column(name = "question_set_id")
    private UUID questionSetId;
    
    private String title;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;
    
    @Enumerated(EnumType.STRING)
    private PrivacyLevel privacyLevel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
    
    @ElementCollection
    @CollectionTable(name = "feedback_responses", 
        joinColumns = @JoinColumn(name = "feedback_id"))
    @MapKeyColumn(name = "question_id")
    private Map<UUID, Answer> responses;
    
    private Double rating;
    private String additionalComments;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}