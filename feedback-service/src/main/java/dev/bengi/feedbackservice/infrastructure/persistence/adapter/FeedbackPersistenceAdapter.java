package dev.bengi.feedbackservice.infrastructure.persistence.adapter;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.FeedbackMapper;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeedbackPersistenceAdapter {
    private final FeedbackRepository feedbackRepository;
    private final FeedbackMapper feedbackMapper;

    public List<Feedback> findByProjectId(UUID projectId) {
        return feedbackRepository.findByProjectId(projectId);
    }

    public List<Feedback> findWithFilters(
            UUID projectId,
            QuestionCategory category,
            QuestionSentiment sentiment,
            String privacyLevel,
            String searchTerm
    ) {
        return feedbackRepository.findWithFilters(
            projectId,
            category,
            sentiment,
            privacyLevel,
            searchTerm
        );
    }

    public Double getAverageRatingByProjectId(UUID projectId) {
        return feedbackRepository.getAverageRatingByProjectId(projectId);
    }

    public Double getAverageRatingByProjectIdAndCategory(UUID projectId, QuestionCategory category) {
        return feedbackRepository.getAverageRatingByProjectIdAndCategory(projectId, category);
    }

    public Page<Feedback> searchFeedbacks(dev.bengi.feedbackservice.domain.model.FeedbackSearch search, Pageable pageable) {
        return feedbackRepository.searchFeedback(search, pageable);
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return feedbackRepository.findBySubmittedAtBetween(startDate, endDate);
    }

    public List<Feedback> findTopByOrderBySubmittedAtDesc(int limit) {
        return feedbackRepository.findTopByOrderBySubmittedAtDesc(limit);
    }

    public List<Feedback> findByUserId(UUID userId) {
        return feedbackRepository.findByUserId(userId);
    }

    public List<Feedback> findByUserIdAndProjectId(UUID userId, UUID projectId) {
        return feedbackRepository.findByUserIdAndProjectId(userId, projectId);
    }

    public Optional<Feedback> findByIdAndUserId(UUID feedbackId, UUID userId) {
        return feedbackRepository.findByIdAndUserId(feedbackId, userId);
    }

    public List<Feedback> findByProjectIdAndQuestionSetId(UUID projectId, UUID questionSetId) {
        return feedbackRepository.findByProjectIdAndQuestionSetId(projectId, questionSetId);
    }
} 