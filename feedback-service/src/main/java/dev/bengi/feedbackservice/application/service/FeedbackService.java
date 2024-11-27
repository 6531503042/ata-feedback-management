package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.dto.FeedbackRequest;
import dev.bengi.feedbackservice.application.dto.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.application.dto.feedback.FeedbackResponse;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackAnswer;
import dev.bengi.feedbackservice.domain.model.QuestionSet;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.FeedbackPersistenceAdapter;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.QuestionSetPersistenceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackPersistenceAdapter feedbackPersistenceAdapter;
    private final QuestionSetPersistenceAdapter questionSetPersistenceAdapter;
    
    public Feedback createFeedback(FeedbackRequest request) {
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

    public Page<Feedback> searchFeedbacks(dev.bengi.feedbackservice.domain.model.FeedbackSearch search, Pageable pageable) {
        return feedbackPersistenceAdapter.searchFeedbacks(search, pageable);
    }

    public Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request) {
        // Validate question set exists
        QuestionSet questionSet = questionSetPersistenceAdapter.findById(request.getQuestionSetId())
            .orElseThrow(() -> new RuntimeException("Question set not found"));

        // Validate project
        if (!questionSet.getProjectId().equals(request.getProjectId())) {
            throw new RuntimeException("Question set does not belong to the specified project");
        }

        LocalDateTime now = LocalDateTime.now();
        Feedback feedback = Feedback.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .projectId(request.getProjectId())
                .questionSetId(request.getQuestionSetId())
                .responses(mapResponses(request.getResponses()))
                .additionalComments(request.getAdditionalComments())
                .submittedAt(request.getSubmittedAt() != null ? request.getSubmittedAt() : now)
                .createdAt(now)
                .updatedAt(now)
                .build();
                
        return feedbackPersistenceAdapter.save(feedback);
    }

    public List<Feedback> getUserFeedbacks(UUID userId) {
        return feedbackPersistenceAdapter.findByUserId(userId);
    }

    public List<Feedback> getUserFeedbacksByProject(UUID userId, UUID projectId) {
        return feedbackPersistenceAdapter.findByUserIdAndProjectId(userId, projectId);
    }

    public Feedback getUserFeedbackById(UUID userId, UUID feedbackId) {
        return feedbackPersistenceAdapter.findByIdAndUserId(feedbackId, userId)
            .orElseThrow(() -> new RuntimeException("Feedback not found"));
    }

    public List<Feedback> getFeedbacksByQuestionSet(UUID projectId, UUID questionSetId) {
        return feedbackPersistenceAdapter.findByProjectIdAndQuestionSetId(projectId, questionSetId);
    }

    private Map<UUID, FeedbackAnswer> mapResponses(List<FeedbackResponse> responses) {
        return responses.stream()
            .collect(Collectors.toMap(
                FeedbackResponse::getQuestionId,
                response -> new FeedbackAnswer(
                    response.getType(),
                    response.getRatingValue(),
                    response.getTextValue()
                )
            ));
    }
}