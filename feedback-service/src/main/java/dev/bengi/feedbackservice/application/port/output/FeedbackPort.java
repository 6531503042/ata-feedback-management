package dev.bengi.feedbackservice.application.port.output;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;

import java.util.List;
import java.util.UUID;

public interface FeedbackPort {
    Feedback save(Feedback feedback);
    List<Feedback> findByProjectId(UUID projectId);
    List<Feedback> findWithFilters(
        UUID projectId,
        QuestionCategory category,
        QuestionSentiment sentiment,
        String privacyLevel,
        String searchTerm
    );
    long countByProjectId(UUID projectId);
    double getAverageRatingByProjectId(UUID projectId);
    double getAverageRatingByProjectIdAndCategory(UUID projectId, QuestionCategory category);
    List<Feedback> findRecentFeedback(int limit);
    List<Feedback> findByYear(int year);
} 