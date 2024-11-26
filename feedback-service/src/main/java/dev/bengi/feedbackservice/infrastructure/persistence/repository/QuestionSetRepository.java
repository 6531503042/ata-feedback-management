package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.domain.model.QuestionSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface QuestionSetRepository extends JpaRepository<QuestionSet, UUID> {
} 