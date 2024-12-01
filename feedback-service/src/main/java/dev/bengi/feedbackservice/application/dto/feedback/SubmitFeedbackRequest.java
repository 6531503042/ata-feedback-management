package dev.bengi.feedbackservice.application.dto.feedback;

import dev.bengi.feedbackservice.domain.model.enums.PrivacyLevel;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.presentation.dto.request.FeedbackResponseRequest;
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
    private List<FeedbackResponseRequest> responses;
    
    private String additionalComments;
    private LocalDateTime submittedAt;
} 