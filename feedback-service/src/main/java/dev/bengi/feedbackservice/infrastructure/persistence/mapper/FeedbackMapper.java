package dev.bengi.feedbackservice.infrastructure.persistence.mapper;

import dev.bengi.feedbackservice.application.dto.FeedbackRequest;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
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
                .submittedAt(feedback.getSubmittedAt())
                .createdAt(feedback.getCreatedAt())
                .updatedAt(feedback.getUpdatedAt())
                .build();
    }

    public Feedback toDomain(FeedbackRequest request) {
        return Feedback.builder()
                .projectId(request.getProjectId())
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory() != null ? 
                    QuestionCategory.valueOf(request.getCategory()) : null)
                .privacyLevel(request.getPrivacyLevel())
                .rating(request.getRating())
                .build();
    }
} 