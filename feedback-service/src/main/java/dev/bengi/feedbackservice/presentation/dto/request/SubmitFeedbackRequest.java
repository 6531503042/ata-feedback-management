package dev.bengi.feedbackservice.presentation.dto.request;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitFeedbackRequest {
    private UUID projectId;
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