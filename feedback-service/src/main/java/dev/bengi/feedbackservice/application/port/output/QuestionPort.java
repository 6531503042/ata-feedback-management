package dev.bengi.feedbackservice.application.port.output;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionPort {
    Question save(Question question);
    Optional<Question> findById(UUID id);
    List<Question> findAll();
    List<Question> findByCategory(QuestionCategory category);
    List<Question> findBySentiment(QuestionSentiment sentiment);
    void deleteById(UUID id);
    List<Question> findByIds(List<UUID> ids);
} 