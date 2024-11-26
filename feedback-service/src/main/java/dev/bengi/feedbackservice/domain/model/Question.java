package dev.bengi.feedbackservice.domain.model;

import dev.bengi.feedbackservice.domain.model.enums.AnswerType;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionCategory category;

    @Enumerated(EnumType.STRING)
    private QuestionType type;

    @Enumerated(EnumType.STRING)
    private AnswerType answerType;

    @Enumerated(EnumType.STRING)
    private QuestionSentiment sentiment;

    @ElementCollection
    @CollectionTable(name = "question_options", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "option")
    private List<String> options;

    private boolean required;
    
    private boolean active;

    @Column(length = 1000)
    private String wordBasedPrompt;

    @ManyToMany(mappedBy = "questions")
    private List<Project> projects;
}