package dev.bengi.feedbackservice.infrastructure.persistence.adapter;

import dev.bengi.feedbackservice.domain.model.QuestionSet;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.QuestionSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuestionSetPersistenceAdapter {
    private final QuestionSetRepository questionSetRepository;

    public QuestionSet save(QuestionSet questionSet) {
        return questionSetRepository.save(questionSet);
    }

    public Optional<QuestionSet> findById(UUID id) {
        return questionSetRepository.findById(id);
    }

    public List<QuestionSet> findAll() {
        return questionSetRepository.findAll();
    }

    public void deleteById(UUID id) {
        questionSetRepository.deleteById(id);
    }

    public List<QuestionSet> findByProjectId(UUID projectId) {
        return questionSetRepository.findByProjectId(projectId);
    }

    public boolean existsById(UUID id) {
        return questionSetRepository.existsById(id);
    }
} 