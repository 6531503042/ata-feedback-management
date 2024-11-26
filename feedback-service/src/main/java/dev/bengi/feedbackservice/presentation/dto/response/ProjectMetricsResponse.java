package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class ProjectMetricsResponse {
    private int totalFeedback;
    private double averageRating;
    private double responseRate;
    private double overallSentiment;
    private int pendingActions;
    
    private Map<String, CategoryMetric> categoryMetrics;
    
    @Data
    @Builder
    public static class CategoryMetric {
        private double currentPerformance;
        private double targetPerformance;
    }
} 