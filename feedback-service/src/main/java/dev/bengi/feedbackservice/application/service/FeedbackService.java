package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.application.port.output.FeedbackPort;
import dev.bengi.feedbackservice.application.port.output.ProjectPort;
import dev.bengi.feedbackservice.domain.exception.ResourceNotFoundException;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.Project;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectMetricsResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectMetricsResponse.CategoryMetric;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackService implements FeedbackUseCase {
    private final FeedbackPort feedbackPort;
    private final ProjectPort projectPort;

    @Override
    @Transactional
    public Feedback submitFeedback(UUID userId, SubmitFeedbackRequest request) {
        Feedback feedback = Feedback.builder()
                .userId(userId)
                .projectId(request.getProjectId())
                .answers(request.getAnswers())
                .privacyLevel(request.getPrivacyLevel())
                .submittedAt(LocalDateTime.now())
                .build();

        return feedbackPort.save(feedback);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getFeedbacksByProject(UUID projectId) {
        return feedbackPort.findByProjectId(projectId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getFeedbacksWithFilters(UUID projectId, QuestionCategory category, 
            QuestionSentiment sentiment, String privacyLevel, String searchTerm) {
        return feedbackPort.findWithFilters(projectId, category, sentiment, privacyLevel, searchTerm);
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackAnalyticsResponse getProjectAnalytics(UUID projectId) {
        Map<QuestionCategory, Double> categoryAverages = new HashMap<>();
        for (QuestionCategory category : QuestionCategory.values()) {
            double average = feedbackPort.getAverageRatingByProjectIdAndCategory(projectId, category);
            categoryAverages.put(category, average);
        }

        Map<String, Integer> participationStats = new HashMap<>();
        List<Feedback> feedbacks = feedbackPort.findByProjectId(projectId);
        participationStats.put("uniqueParticipants", 
            (int) feedbacks.stream().map(Feedback::getUserId).distinct().count());

        Map<String, Object> detailedMetrics = new HashMap<>();
        detailedMetrics.put("averageResponseTime", calculateAverageResponseTime(feedbacks));
        detailedMetrics.put("responseDistribution", calculateResponseDistribution(feedbacks));

        return FeedbackAnalyticsResponse.builder()
                .categoryAverages(categoryAverages)
                .participationStats(participationStats)
                .detailedMetrics(detailedMetrics)
                .totalResponses(feedbacks.size())
                .overallSatisfactionScore(feedbackPort.getAverageRatingByProjectId(projectId))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectMetricsResponse getProjectMetrics(UUID projectId) {
        Project project = projectPort.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        double averageRating = feedbackPort.getAverageRatingByProjectId(projectId);
        long totalFeedback = feedbackPort.countByProjectId(projectId);
        
        Map<String, CategoryMetric> categoryMetrics = new HashMap<>();
        for (QuestionCategory category : QuestionCategory.values()) {
            double categoryAverage = feedbackPort.getAverageRatingByProjectIdAndCategory(projectId, category);
            categoryMetrics.put(category.name(), CategoryMetric.builder()
                    .currentPerformance(categoryAverage)
                    .targetPerformance(85.0)
                    .build());
        }

        double responseRate = calculateResponseRate(project.getTotalEmployees(), 
                project.getParticipatedEmployees());

        return ProjectMetricsResponse.builder()
                .totalFeedback((int) totalFeedback)
                .averageRating(averageRating)
                .responseRate(responseRate)
                .categoryMetrics(categoryMetrics)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public FeedbackAnalyticsResponse getYearlyAnalytics(int year) {
        List<Feedback> yearlyFeedbacks = feedbackPort.findByYear(year);
        
        Map<QuestionCategory, Double> categoryAverages = calculateCategoryAverages(yearlyFeedbacks);
        Map<String, Integer> participationStats = calculateParticipationStats(yearlyFeedbacks);
        Map<String, Object> detailedMetrics = generateDetailedMetrics(yearlyFeedbacks);

        return FeedbackAnalyticsResponse.builder()
                .categoryAverages(categoryAverages)
                .participationStats(participationStats)
                .detailedMetrics(detailedMetrics)
                .totalResponses(yearlyFeedbacks.size())
                .overallSatisfactionScore(calculateOverallSatisfaction(yearlyFeedbacks))
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Feedback> getRecentFeedback(int limit) {
        return feedbackPort.findRecentFeedback(limit);
    }

    @Override
    @Transactional(readOnly = true)
    public byte[] generateYearlyReport(int year) {
        FeedbackAnalyticsResponse analytics = getYearlyAnalytics(year);
        StringBuilder report = new StringBuilder();
        
        report.append(String.format("Yearly Feedback Report %d\n\n", year));
        report.append(String.format("Total Responses: %d\n", analytics.getTotalResponses()));
        report.append(String.format("Overall Satisfaction: %.2f%%\n\n", 
                analytics.getOverallSatisfactionScore()));
        
        report.append("Category Averages:\n");
        analytics.getCategoryAverages().forEach((category, average) -> 
            report.append(String.format("- %s: %.2f%%\n", category, average)));
        
        report.append("\nParticipation Statistics:\n");
        analytics.getParticipationStats().forEach((key, value) ->
            report.append(String.format("- %s: %d\n", key, value)));

        return report.toString().getBytes();
    }

    private double calculateResponseRate(int totalEmployees, Integer participatedEmployees) {
        if (totalEmployees == 0) return 0.0;
        return ((participatedEmployees != null ? participatedEmployees : 0) * 100.0) / totalEmployees;
    }

    private double calculateAverageResponseTime(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .mapToLong(f -> f.getSubmittedAt().getHour())
                .average()
                .orElse(0.0);
    }

    private Map<String, Long> calculateResponseDistribution(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .collect(Collectors.groupingBy(
                    f -> f.getSubmittedAt().getDayOfWeek().toString(),
                    Collectors.counting()
                ));
    }

    private Map<QuestionCategory, Double> calculateCategoryAverages(List<Feedback> feedbacks) {
        Map<QuestionCategory, List<Double>> categoryRatings = new HashMap<>();
        
        feedbacks.forEach(feedback -> {
            if (feedback.getQuestion() != null) {
                QuestionCategory category = feedback.getQuestion().getCategory();
                try {
                    double rating = Double.parseDouble(
                            feedback.getAnswers().get(feedback.getQuestion().getId()));
                    categoryRatings.computeIfAbsent(category, k -> new java.util.ArrayList<>())
                            .add(rating);
                } catch (NumberFormatException ignored) {
                    // Skip non-numeric answers
                }
            }
        });

        return categoryRatings.entrySet().stream()
                .collect(Collectors.toMap(
                    Map.Entry::getKey,
                    e -> e.getValue().stream()
                            .mapToDouble(Double::doubleValue)
                            .average()
                            .orElse(0.0)
                ));
    }

    private Map<String, Integer> calculateParticipationStats(List<Feedback> feedbacks) {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("uniqueParticipants", 
                (int) feedbacks.stream().map(Feedback::getUserId).distinct().count());
        return stats;
    }

    private Map<String, Object> generateDetailedMetrics(List<Feedback> feedbacks) {
        Map<String, Object> metrics = new HashMap<>();
        metrics.put("averageResponseTime", calculateAverageResponseTime(feedbacks));
        metrics.put("responseDistribution", calculateResponseDistribution(feedbacks));
        return metrics;
    }

    private double calculateOverallSatisfaction(List<Feedback> feedbacks) {
        return feedbacks.stream()
                .filter(f -> f.getQuestion() != null && 
                        f.getQuestion().getType() == QuestionType.RATING)
                .mapToDouble(f -> {
                    try {
                        return Double.parseDouble(
                                f.getAnswers().get(f.getQuestion().getId()));
                    } catch (NumberFormatException e) {
                        return 0.0;
                    }
                })
                .average()
                .orElse(0.0);
    }
}