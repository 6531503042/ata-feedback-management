package dev.bengi.feedbackservice.infrastructure.persistence.mapper;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.presentation.dto.request.FeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackResponse;
import org.springframework.stereotype.Component;

@Component
public class FeedbackMapper {
    public FeedbackResponse toResponse(Feedback feedback) {
        return FeedbackResponse.builder()
                .id(feedback.getId())
                .projectId(feedback.getProjectId())
                .userId(feedback.getUserId())
                .title(feedback.getTitle())
                .description(feedback.getDescription())
                .category(feedback.getCategory() != null ? feedback.getCategory().name() : null)
                .privacyLevel(feedback.getPrivacyLevel() != null ? feedback.getPrivacyLevel().name() : null)
                .rating(feedback.getRating())
                .build();
    }

    public Feedback toEntity(FeedbackRequest request) {
        return Feedback.builder()
                .projectId(request.getProjectId())
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .privacyLevel(request.getPrivacyLevel())
                .rating(request.getRating().doubleValue())
                .build();
    }
} 