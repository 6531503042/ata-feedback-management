package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface QuestionRepository extends JpaRepository<Question, UUID> {
    List<Question> findByProjectId(UUID projectId);
    List<Question> findByCategory(QuestionCategory category);
    List<Question> findByType(QuestionType type);
    List<Question> findByTextContainingIgnoreCase(String searchTerm);
    List<Question> findBySentiment(QuestionSentiment sentiment);
    
    @Query("SELECT q FROM Question q JOIN q.questionSet qs WHERE qs.id = :setId")
    List<Question> findByQuestionSetId(@Param("setId") UUID setId);
} 