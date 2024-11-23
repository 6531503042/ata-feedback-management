package dev.bengi.feedbackservice.presentation.dto.response;

import dev.bengi.feedbackservice.domain.model.QuestionCategory;
import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class FeedbackAnalyticsResponse {
    private Map<QuestionCategory, Double> categoryAverages;
    private Map<String, Integer> participationStats;
    private Map<String, Object> detailedMetrics;
    private int totalResponses;
    private double overallSatisfactionScore;
}