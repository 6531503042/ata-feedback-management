package dev.bengi.feedbackservice.presentation.dto.request;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FeedbackRequest {
    private UUID projectId;
    private UUID userId;
    private UUID questionSetId;
    private String title;
    private String description;
    private QuestionCategory category;
    private PrivacyLevel privacyLevel;
    private Double rating;
    private String additionalComments;
    private LocalDateTime submittedAt;
    private List<FeedbackResponseRequest> responses;
} 