package dev.bengi.feedbackservice.infrastructure.persistence.adapter;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackSearch;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.FeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class FeedbackPersistenceAdapter {
    private final FeedbackRepository feedbackRepository;

    public List<Feedback> searchFeedbacks(
        UUID projectId,
        QuestionCategory category,
        boolean sentimentAnalysis,
        String privacyLevel,
        String searchTerm
    ) {
        return feedbackRepository.findAll(); // Placeholder implementation
    }

    public Page<Feedback> searchFeedbacks(FeedbackSearch search, Pageable pageable) {
        return feedbackRepository.findAll(pageable);
    }

    public Feedback save(Feedback feedback) {
        return feedbackRepository.save(feedback);
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

    public List<Feedback> findByProjectId(UUID projectId) {
        return feedbackRepository.findByProjectId(projectId);
    }
} 