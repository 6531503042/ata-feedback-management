package dev.bengi.feedbackservice.application.port.output;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;

import java.util.List;
import java.util.UUID;

public interface QuestionPort {
    List<Question> findByType(QuestionType type);
    List<Question> findByCategory(QuestionCategory category);
    List<Question> findBySentimentAnalysis(boolean sentimentAnalysis);
    List<Question> findByProjectId(UUID projectId);
} 