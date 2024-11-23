package dev.bengi.feedbackservice.application.port.input;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;

import java.util.List;
import java.util.UUID;

public interface FeedbackUseCase {
    Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request);

    List<Feedback> getFeedbacksByProject(UUID projectId);

    FeedbackAnalyticsResponse getProjectAnalytics(UUID projectId);

    FeedbackAnalyticsResponse getYearlyAnalytics(int year);

    byte[] generateYearlyReport(int year);
}