package dev.bengi.feedbackservice.presentation.dto.request;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateProjectRequest {
    @NotBlank(message = "Project name is required")
    private String name;
    
    private String description;
    private LocalDateTime feedbackStartDate;
    private LocalDateTime feedbackEndDate;
    private Integer totalEmployees;
}