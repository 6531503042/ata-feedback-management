package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackSearch;

public interface FeedbackRepositoryCustom {
    Page<Feedback> searchFeedback(FeedbackSearch search, Pageable pageable);
} 