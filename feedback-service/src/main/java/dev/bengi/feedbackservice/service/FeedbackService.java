package dev.bengi.feedbackservice.service;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackService implements FeedbackUseCase {
    @Override
    public Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request) {
        // Implementation
        return null;
    }

    @Override
    public List<Feedback> getFeedbacksByProject(UUID projectId) {
        // Implementation
        return null;
    }

    @Override
    public FeedbackAnalyticsResponse getProjectAnalytics(UUID projectId) {
        // Implementation
        return null;
    }

    @Override
    public FeedbackAnalyticsResponse getYearlyAnalytics(int year) {
        // Implementation
        return null;
    }

    @Override
    public byte[] generateYearlyReport(int year) {
        // Implementation for PDF generation
        return new byte[0];
    }
}