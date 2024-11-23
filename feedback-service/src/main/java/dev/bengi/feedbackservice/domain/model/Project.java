package dev.bengi.feedbackservice.domain.model;

import jakarta.persistence.*;
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
@Table(name = "projects")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;
    private String description;
    private LocalDateTime feedbackStartDate;
    private LocalDateTime feedbackEndDate;
    private boolean active;

    @ManyToMany
    @JoinTable(name = "project_questions", joinColumns = @JoinColumn(name = "project_id"), inverseJoinColumns = @JoinColumn(name = "question_id"))
    private List<Question> questions;
}