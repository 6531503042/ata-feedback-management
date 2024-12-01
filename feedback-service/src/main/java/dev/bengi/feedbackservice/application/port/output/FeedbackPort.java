package dev.bengi.feedbackservice.application.port.output;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface FeedbackPort {
    List<Feedback> findWithFilters(
        UUID projectId,
        QuestionCategory category,
        boolean sentimentAnalysis,
        String privacyLevel,
        String searchTerm
    );
} 