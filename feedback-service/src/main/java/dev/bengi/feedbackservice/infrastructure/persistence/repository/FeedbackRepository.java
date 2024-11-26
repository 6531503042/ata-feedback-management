package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    List<Feedback> findByProjectId(UUID projectId);
    
    @Query("SELECT f FROM Feedback f WHERE f.projectId = :projectId " +
           "AND (:category IS NULL OR f.question.category = :category) " +
           "AND (:sentiment IS NULL OR f.question.sentiment = :sentiment) " +
           "AND (:privacyLevel IS NULL OR f.privacyLevel = :privacyLevel) " +
           "AND (:searchTerm IS NULL OR LOWER(f.answers) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Feedback> findWithFilters(
        @Param("projectId") UUID projectId,
        @Param("category") QuestionCategory category,
        @Param("sentiment") QuestionSentiment sentiment,
        @Param("privacyLevel") String privacyLevel,
        @Param("searchTerm") String searchTerm
    );

    @Query(value = "SELECT * FROM feedbacks ORDER BY submitted_at DESC LIMIT :limit", nativeQuery = true)
    List<Feedback> findTopByOrderBySubmittedAtDesc(@Param("limit") int limit);

    List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);

    long countByProjectId(UUID projectId);

    @Query("SELECT AVG(CAST(f.answers as double)) FROM Feedback f " +
           "WHERE f.projectId = :projectId AND f.question.type = 'RATING'")
    double getAverageRatingByProjectId(@Param("projectId") UUID projectId);

    @Query("SELECT AVG(CAST(f.answers as double)) FROM Feedback f " +
           "WHERE f.projectId = :projectId " +
           "AND f.question.category = :category " +
           "AND f.question.type = 'RATING'")
    double getAverageRatingByProjectIdAndCategory(
        @Param("projectId") UUID projectId,
        @Param("category") QuestionCategory category
    );
} 