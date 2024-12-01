package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.dto.feedback.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.domain.model.Answer;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackSearch;
import dev.bengi.feedbackservice.domain.model.QuestionSet;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.FeedbackPersistenceAdapter;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.QuestionSetPersistenceAdapter;
import dev.bengi.feedbackservice.presentation.dto.request.FeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final FeedbackPersistenceAdapter feedbackPersistenceAdapter;
    private final QuestionSetPersistenceAdapter questionSetPersistenceAdapter;
    
    public Feedback createFeedback(FeedbackRequest request) {
        QuestionCategory category = null;
        if (request.getCategory() != null) {
            try {
                category = request.getCategory();
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Invalid category: " + request.getCategory());
            }
        }

        return Feedback.builder()
                .projectId(request.getProjectId())
                .userId(request.getUserId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(category)
                .privacyLevel(request.getPrivacyLevel())
                .rating(request.getRating().doubleValue())
                .build();
    }

    public Page<Feedback> searchFeedbacks(FeedbackSearch search, Pageable pageable) {
        return feedbackPersistenceAdapter.searchFeedbacks(search, pageable);
    }

    public Feedback submitFeedback(UUID userId, FeedbackRequest request) {
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
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .privacyLevel(request.getPrivacyLevel())
                .rating(request.getRating().doubleValue())
                .additionalComments(request.getAdditionalComments())
                .submittedAt(request.getSubmittedAt() != null ? request.getSubmittedAt() : now)
                .createdAt(now)
                .updatedAt(now)
                .build();

        // Add answers after building the feedback
        if (request.getResponses() != null) {
            request.getResponses().forEach(response -> {
                Answer answer = Answer.builder()
                    .id(UUID.randomUUID())
                    .type(response.getType())
                    .ratingValue(response.getRatingValue())
                    .build();
                feedback.addAnswer(answer);
            });
        }
                
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
}