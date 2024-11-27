package dev.bengi.feedbackservice.presentation.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class AssignQuestionSetRequest {
    @NotNull
    private UUID projectId;
} 