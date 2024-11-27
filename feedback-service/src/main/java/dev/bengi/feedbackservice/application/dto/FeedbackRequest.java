package dev.bengi.feedbackservice.application.dto;

import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import lombok.Data;
import java.util.UUID;

@Data
public class FeedbackRequest {
    private UUID projectId;
    private UUID userId;
    private String title;
    private String description;
    private String category;
    private PrivacyLevel privacyLevel;
    private Double rating;
} 