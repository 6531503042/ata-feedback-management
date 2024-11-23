package dev.bengi.feedbackservice.presentation.dto.request;

import dev.bengi.feedbackservice.domain.model.PrivacyLevel;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
public class SubmitFeedbackRequest {
    @NotNull(message = "Project ID is required")
    private UUID projectId;

    @NotNull(message = "Answers are required")
    private Map<UUID, String> answers; // QuestionId -> Answer

    @NotNull(message = "Privacy level is required")
    private PrivacyLevel privacyLevel;
}