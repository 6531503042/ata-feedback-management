package dev.bengi.feedbackservice.domain.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank
    private String name;
    
    private String description;
    
    @ManyToMany
    @JoinTable(
        name = "question_set_questions",
        joinColumns = @JoinColumn(name = "set_id"),
        inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private List<Question> questions;
    
    private boolean active;
    
    private LocalDateTime startDate;
    
    private LocalDateTime endDate;
    
    private UUID projectId;
} 