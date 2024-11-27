package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private UUID id;
    private UUID projectId;
    private UUID userId;
    private String title;
    private String description;
    private String category;
    private String privacyLevel;
    private QuestionResponse question;
    private Map<UUID, String> answers;
    private Double rating;
    private LocalDateTime submittedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 