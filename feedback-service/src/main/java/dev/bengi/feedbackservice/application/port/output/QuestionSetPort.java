package dev.bengi.feedbackservice.application.port.output;

import dev.bengi.feedbackservice.domain.model.QuestionSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuestionSetPort {
    QuestionSet save(QuestionSet questionSet);
    Optional<QuestionSet> findById(UUID id);
    List<QuestionSet> findAll();
    void deleteById(UUID id);
    List<QuestionSet> findByProjectId(UUID projectId);
    boolean existsByName(String name);
} 