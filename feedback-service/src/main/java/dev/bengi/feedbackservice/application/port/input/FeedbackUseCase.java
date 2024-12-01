package dev.bengi.feedbackservice.application.port.input;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectMetricsResponse;

public interface FeedbackUseCase {
    List<Feedback> searchFeedbacks(
        UUID projectId,
        QuestionCategory category,
        boolean sentimentAnalysis,
        String privacyLevel,
        String searchTerm
    );

    List<Feedback> getFeedbacksByProject(UUID projectId);

    List<Feedback> findWithFilters(
        UUID projectId,
        QuestionCategory category,
        boolean sentimentAnalysis,
        String privacyLevel,
        String searchTerm
    );

    List<Feedback> getFeedbacksWithFilters(
        UUID projectId,
        QuestionCategory category,
        boolean sentimentAnalysis,
        String privacyLevel,
        String searchTerm
    );

    Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request);

    FeedbackAnalyticsResponse getProjectAnalytics(UUID projectId);

    ProjectMetricsResponse getProjectMetrics(UUID projectId);

    FeedbackAnalyticsResponse getYearlyAnalytics(int year);

    List<Feedback> getRecentFeedback(int limit);

    byte[] generateYearlyReport(int year);

    List<Feedback> findByProjectId(UUID projectId);

    List<Feedback> findTopByOrderBySubmittedAtDesc(int limit);

    List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    Double getAverageRatingByProjectId(UUID projectId);

    Double getAverageRatingByProjectIdAndCategory(UUID projectId, QuestionCategory category);
}