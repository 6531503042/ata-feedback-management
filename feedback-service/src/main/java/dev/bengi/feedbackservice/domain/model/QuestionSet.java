package dev.bengi.feedbackservice.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question_sets")
public class QuestionSet {
    @Id
    private UUID id;
    
    private String name;
    private String description;
    
    @Column(name = "project_id")
    private UUID projectId;
    
    private boolean active;
    
    @Builder.Default
    @OneToMany(mappedBy = "questionSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
    
    public void addQuestion(Question question) {
        questions.add(question);
        question.setQuestionSet(this);
    }
    
    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setQuestionSet(null);
    }
} 