package dev.bengi.feedbackservice.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class FeedbackEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    
    @Column(name = "project_id")
    private UUID projectId;
    
    @Column(name = "user_id")
    private UUID userId;
    
    private String title;
    private String description;
    private String category;
    private String privacyLevel;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private QuestionEntity question;
    
    @ElementCollection
    @CollectionTable(name = "feedback_answers", 
        joinColumns = @JoinColumn(name = "feedback_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "answer")
    private Map<UUID, String> answers;
    
    private Double rating;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 