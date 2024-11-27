package dev.bengi.feedbackservice.application.dto;

import dev.bengi.feedbackservice.application.dto.feedback.FeedbackResponse;
import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private UUID questionSetId;
    
    @NotNull
    private UUID projectId;
    
    private String title;
    private String description;
    private QuestionCategory category;
    private PrivacyLevel privacyLevel;
    private Double rating;
    
    @NotNull
    private List<FeedbackResponse> responses;
    
    private String additionalComments;
    private LocalDateTime submittedAt;
} 