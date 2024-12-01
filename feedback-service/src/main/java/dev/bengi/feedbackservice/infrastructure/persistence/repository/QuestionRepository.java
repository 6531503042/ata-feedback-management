package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByType(QuestionType type);
    List<Question> findByCategory(QuestionCategory category);
    List<Question> findBySentimentAnalysis(boolean sentimentAnalysis);
    List<Question> findByProjectId(UUID projectId);
    List<Question> findByTextContainingIgnoreCase(String searchTerm);
} 