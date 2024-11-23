package dev.bengi.feedbackservice.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Value
@Builder
public class Feedback {
    UUID id;
    UUID projectId;
    UUID userId; // Can be null for anonymous feedback
    Map<UUID, String> answers; // QuestionId -> Answer
    PrivacyLevel privacyLevel;
    LocalDateTime submittedAt;
}