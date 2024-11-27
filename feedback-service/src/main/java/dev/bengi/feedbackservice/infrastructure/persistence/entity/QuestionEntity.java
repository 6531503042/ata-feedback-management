package dev.bengi.feedbackservice.infrastructure.persistence.entity;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "questions")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String text;
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    private QuestionSentiment sentiment;

    @ElementCollection
    @CollectionTable(name = "question_options", 
        joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option")
    private List<String> options;

    private boolean required;
    private boolean active;
    private String wordBasedPrompt;
    private String answerType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_set_id")
    private QuestionSetEntity questionSet;
} 