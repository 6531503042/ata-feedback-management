package dev.bengi.feedbackservice.presentation.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class CreateQuestionSetRequest {
    @NotEmpty(message = "Question set name is required")
    private String name;
    
    private String description;
    
    @NotEmpty(message = "At least one question ID is required")
    private List<UUID> questionIds;
} 