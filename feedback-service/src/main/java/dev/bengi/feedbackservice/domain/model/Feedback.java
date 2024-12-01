package dev.bengi.feedbackservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    
    @OneToMany(mappedBy = "feedback", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Answer> answers = new ArrayList<>();
    
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

    public void addAnswer(Answer answer) {
        answers.add(answer);
        answer.setFeedback(this);
    }

    public void removeAnswer(Answer answer) {
        answers.remove(answer);
        answer.setFeedback(null);
    }
}