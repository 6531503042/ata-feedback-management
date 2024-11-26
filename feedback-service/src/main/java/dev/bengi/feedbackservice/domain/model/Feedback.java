package dev.bengi.feedbackservice.domain.model;

import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedbacks")
public class Feedback {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "project_id")
    private UUID projectId;

    @Column(name = "user_id")
    private UUID userId;

    @ElementCollection
    @CollectionTable(name = "feedback_answers", 
        joinColumns = @JoinColumn(name = "feedback_id"))
    @MapKeyColumn(name = "question_id")
    @Column(name = "answer")
    private Map<UUID, String> answers;

    @Enumerated(EnumType.STRING)
    private PrivacyLevel privacyLevel;

    private LocalDateTime submittedAt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}