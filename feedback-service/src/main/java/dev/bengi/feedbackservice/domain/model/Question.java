package dev.bengi.feedbackservice.domain.model;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
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
@Table(name = "questions")
public class Question {
    @Id
    private UUID id;
    
    private String text;
    
    @Enumerated(EnumType.STRING)
    private QuestionType type;
    
    @Enumerated(EnumType.STRING)
    private QuestionCategory category;
    
    @Enumerated(EnumType.STRING)
    private QuestionSentiment sentiment;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    private Project project;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_set_id")
    private QuestionSet questionSet;
    
    public void setProject(Project project) {
        this.project = project;
    }
    
    public void setQuestionSet(QuestionSet questionSet) {
        this.questionSet = questionSet;
    }
}