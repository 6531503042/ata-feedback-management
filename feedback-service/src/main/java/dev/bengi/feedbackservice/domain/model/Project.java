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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "projects")
public class Project {
    @Id
    private UUID id;
    
    private String name;
    private String description;
    
    @Builder.Default
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();
    
    private LocalDateTime feedbackStartDate;
    private LocalDateTime feedbackEndDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private Integer totalEmployees;
    private Integer participatedEmployees;
    private boolean active;
    
    public void addQuestion(Question question) {
        questions.add(question);
        question.setProject(this);
    }
    
    public void removeQuestion(Question question) {
        questions.remove(question);
        question.setProject(null);
    }
}