package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.bengi.feedbackservice.application.dto.FeedbackSearch;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, UUID> {
    
    List<Feedback> findByProjectId(UUID projectId);
    
    @Query("SELECT f FROM Feedback f WHERE " +
           "(:projectId is null OR f.projectId = :projectId) AND " +
           "(:category is null OR f.category = :category) AND " +
           "(:sentiment is null OR f.question.sentiment = :sentiment) AND " +
           "(:privacyLevel is null OR f.privacyLevel = :privacyLevel) AND " +
           "(:searchTerm is null OR LOWER(f.description) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Feedback> findWithFilters(
            @Param("projectId") UUID projectId,
            @Param("category") QuestionCategory category,
            @Param("sentiment") QuestionSentiment sentiment,
            @Param("privacyLevel") String privacyLevel,
            @Param("searchTerm") String searchTerm
    );
    
    Long countByProjectId(UUID projectId);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.projectId = :projectId")
    Double getAverageRatingByProjectId(@Param("projectId") UUID projectId);
    
    @Query("SELECT AVG(f.rating) FROM Feedback f WHERE f.projectId = :projectId AND f.category = :category")
    Double getAverageRatingByProjectIdAndCategory(
            @Param("projectId") UUID projectId,
            @Param("category") QuestionCategory category
    );
    
    @Query(value = "SELECT f FROM Feedback f ORDER BY f.submittedAt DESC LIMIT :limit", nativeQuery = true)
    List<Feedback> findTopByOrderBySubmittedAtDesc(@Param("limit") int limit);
    
    List<Feedback> findBySubmittedAtBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT f FROM Feedback f WHERE " +
           "(:#{#search.projectId} is null OR f.projectId = :#{#search.projectId}) AND " +
           "(:#{#search.category} is null OR f.category = :#{#search.category}) AND " +
           "(:#{#search.searchTerm} is null OR LOWER(f.description) LIKE LOWER(CONCAT('%', :#{#search.searchTerm}, '%')))")
    Page<Feedback> searchFeedback(@Param("search") dev.bengi.feedbackservice.domain.model.FeedbackSearch search, Pageable pageable);
    
    List<Feedback> findByUserId(UUID userId);
    List<Feedback> findByUserIdAndProjectId(UUID userId, UUID projectId);
    Optional<Feedback> findByIdAndUserId(UUID feedbackId, UUID userId);
    List<Feedback> findByProjectIdAndQuestionSetId(UUID projectId, UUID questionSetId);
} 