package dev.bengi.feedbackservice.presentation.dto.request;

import dev.bengi.feedbackservice.application.dto.feedback.FeedbackAnswerDto;
import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubmitFeedbackRequest {
    private UUID questionSetId;
    private UUID projectId;
    private String title;
    private String description;
    private QuestionCategory category;
    private PrivacyLevel privacyLevel;
    private List<FeedbackAnswerDto> responses;
    private Double rating;
    private String additionalComments;
    private LocalDateTime submittedAt;
}