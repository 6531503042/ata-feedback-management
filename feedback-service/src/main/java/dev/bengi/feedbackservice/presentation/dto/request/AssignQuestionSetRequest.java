package dev.bengi.feedbackservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AssignQuestionSetRequest {
    @NotNull(message = "Project ID is required")
    private UUID projectId;
    
    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;
    
    @NotNull(message = "End date is required")
    private LocalDateTime endDate;
} 