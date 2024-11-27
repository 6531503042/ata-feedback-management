package dev.bengi.feedbackservice.application.dto;

import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class FeedbackResponse {
    private UUID id;
    private UUID projectId;
    private UUID userId;
    private String title;
    private String description;
    private String category;
    private PrivacyLevel privacyLevel;
    private Double rating;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 