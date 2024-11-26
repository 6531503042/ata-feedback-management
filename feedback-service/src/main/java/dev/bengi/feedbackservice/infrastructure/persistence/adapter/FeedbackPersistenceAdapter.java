package dev.bengi.feedbackservice.infrastructure.persistence.adapter;

import dev.bengi.feedbackservice.application.port.output.FeedbackPort;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeedbackPersistenceAdapter implements FeedbackPort {
    private final FeedbackRepository feedbackRepository;

    @Override
    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    @Override
    public List<Feedback> findByProjectId(UUID projectId) {
        return feedbackRepository.findByProjectId(projectId);
    }

    @Override
    public List<Feedback> findWithFilters(UUID projectId, QuestionCategory category, 
            QuestionSentiment sentiment, String privacyLevel, String searchTerm) {
        return feedbackRepository.findWithFilters(projectId, category, sentiment, privacyLevel, searchTerm);
    }

    @Override
    public long countByProjectId(UUID projectId) {
        return feedbackRepository.countByProjectId(projectId);
    }

    @Override
    public double getAverageRatingByProjectId(UUID projectId) {
        return feedbackRepository.getAverageRatingByProjectId(projectId);
    }

    @Override
    public double getAverageRatingByProjectIdAndCategory(UUID projectId, QuestionCategory category) {
        return feedbackRepository.getAverageRatingByProjectIdAndCategory(projectId, category);
    }

    @Override
    public List<Feedback> findRecentFeedback(int limit) {
        return feedbackRepository.findTopByOrderBySubmittedAtDesc(limit);
    }

    @Override
    public List<Feedback> findByYear(int year) {
        LocalDateTime startOfYear = LocalDateTime.of(year, 1, 1, 0, 0);
        LocalDateTime endOfYear = LocalDateTime.of(year, 12, 31, 23, 59, 59);
        return feedbackRepository.findBySubmittedAtBetween(startOfYear, endOfYear);
    }
} 