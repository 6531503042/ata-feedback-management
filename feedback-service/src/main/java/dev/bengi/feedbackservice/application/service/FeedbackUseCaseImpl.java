package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackAnswer;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.FeedbackPersistenceAdapter;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectMetricsResponse;
import dev.bengi.feedbackservice.application.dto.feedback.FeedbackResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackUseCaseImpl implements FeedbackUseCase {
    private final FeedbackPersistenceAdapter feedbackPersistenceAdapter;

    @Override
    public Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request) {
        LocalDateTime now = LocalDateTime.now();
        Feedback feedback = Feedback.builder()
                .id(UUID.randomUUID())
                .userId(userId)
                .projectId(request.getProjectId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory() != null ? 
                    QuestionCategory.valueOf(request.getCategory()) : null)
                .privacyLevel(request.getPrivacyLevel())
                .rating(request.getRating())
                .responses(mapResponses(request.getResponses()))
                .submittedAt(now)
                .createdAt(now)
                .updatedAt(now)
                .build();
                
        return feedbackPersistenceAdapter.save(feedback);
    }

    private Map<UUID, FeedbackAnswer> mapResponses(List<FeedbackResponse> responses) {
        if (responses == null) {
            return Map.of();
        }
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

    @Override
    public List<Feedback> getFeedbacksByProject(UUID projectId) {
        return feedbackPersistenceAdapter.findByProjectId(projectId);
    }

    @Override
    public List<Feedback> getFeedbacksWithFilters(
            UUID projectId,
            QuestionCategory category,
            QuestionSentiment sentiment,
            String privacyLevel,
            String searchTerm
    ) {
        return feedbackPersistenceAdapter.findWithFilters(
            projectId,
            category,
            sentiment,
            privacyLevel,
            searchTerm
        );
    }

    @Override
    public FeedbackAnalyticsResponse getProjectAnalytics(UUID projectId) {
        // TODO: Implement project analytics
        return null;
    }

    @Override
    public ProjectMetricsResponse getProjectMetrics(UUID projectId) {
        // TODO: Implement project metrics
        return null;
    }

    @Override
    public FeedbackAnalyticsResponse getYearlyAnalytics(int year) {
        // TODO: Implement yearly analytics
        return null;
    }

    @Override
    public List<Feedback> getRecentFeedback(int limit) {
        return feedbackPersistenceAdapter.findTopByOrderBySubmittedAtDesc(limit);
    }

    @Override
    public byte[] generateYearlyReport(int year) {
        // TODO: Implement yearly report generation
        return new byte[0];
    }

    @Override
    public List<Feedback> findByProjectId(UUID projectId) {
        return feedbackPersistenceAdapter.findByProjectId(projectId);
    }

    @Override
    public List<Feedback> findWithFilters(
            UUID projectId,
            QuestionCategory category,
            QuestionSentiment sentiment,
            String privacyLevel,
            String searchTerm
    ) {
        return feedbackPersistenceAdapter.findWithFilters(
            projectId,
            category,
            sentiment,
            privacyLevel,
            searchTerm
        );
    }

    @Override
    public Double getAverageRatingByProjectId(UUID projectId) {
        return feedbackPersistenceAdapter.getAverageRatingByProjectId(projectId);
    }

    @Override
    public Double getAverageRatingByProjectIdAndCategory(UUID projectId, QuestionCategory category) {
        return feedbackPersistenceAdapter.getAverageRatingByProjectIdAndCategory(projectId, category);
    }

    @Override
    public List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return feedbackPersistenceAdapter.findBySubmittedAtBetween(startDate, endDate);
    }

    @Override
    public List<Feedback> findTopByOrderBySubmittedAtDesc(int limit) {
        return feedbackPersistenceAdapter.findTopByOrderBySubmittedAtDesc(limit);
    }
} 