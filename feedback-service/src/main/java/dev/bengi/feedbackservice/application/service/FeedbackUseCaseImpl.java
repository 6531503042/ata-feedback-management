package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.FeedbackPersistenceAdapter;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectMetricsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackUseCaseImpl implements FeedbackUseCase {
    private final FeedbackPersistenceAdapter feedbackPersistenceAdapter;

    @Override
    public List<Feedback> searchFeedbacks(
        UUID projectId,
        QuestionCategory category,
        boolean sentimentAnalysis,
        String privacyLevel,
        String searchTerm
    ) {
        return feedbackPersistenceAdapter.searchFeedbacks(
            projectId, category, sentimentAnalysis, privacyLevel, searchTerm);
    }

    @Override
    public List<Feedback> getFeedbacksByProject(UUID projectId) {
        return feedbackPersistenceAdapter.findByProjectId(projectId);
    }

    @Override
    public List<Feedback> findWithFilters(UUID projectId, QuestionCategory category, boolean sentimentAnalysis, String privacyLevel, String searchTerm) {
        return searchFeedbacks(projectId, category, sentimentAnalysis, privacyLevel, searchTerm);
    }

    @Override
    public List<Feedback> getFeedbacksWithFilters(UUID projectId, QuestionCategory category, boolean sentimentAnalysis, String privacyLevel, String searchTerm) {
        return searchFeedbacks(projectId, category, sentimentAnalysis, privacyLevel, searchTerm);
    }

    @Override
    public Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public FeedbackAnalyticsResponse getProjectAnalytics(UUID projectId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public ProjectMetricsResponse getProjectMetrics(UUID projectId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public FeedbackAnalyticsResponse getYearlyAnalytics(int year) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Feedback> getRecentFeedback(int limit) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public byte[] generateYearlyReport(int year) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Feedback> findByProjectId(UUID projectId) {
        return feedbackPersistenceAdapter.findByProjectId(projectId);
    }

    @Override
    public List<Feedback> findTopByOrderBySubmittedAtDesc(int limit) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Double getAverageRatingByProjectId(UUID projectId) {
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Double getAverageRatingByProjectIdAndCategory(UUID projectId, QuestionCategory category) {
        throw new UnsupportedOperationException("Not implemented yet");
    }
} 