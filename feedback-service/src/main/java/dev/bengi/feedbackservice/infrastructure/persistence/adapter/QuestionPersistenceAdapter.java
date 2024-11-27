package dev.bengi.feedbackservice.infrastructure.persistence.adapter;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class QuestionPersistenceAdapter {
    private final QuestionRepository questionRepository;

    public Question save(Question question) {
        return questionRepository.save(question);
    }

    public Optional<Question> findById(UUID id) {
        return questionRepository.findById(id);
    }

    public void deleteById(UUID id) {
        questionRepository.deleteById(id);
    }

    public List<Question> findByProjectId(UUID projectId) {
        return questionRepository.findByProjectId(projectId);
    }

    public List<Question> findByCategory(QuestionCategory category) {
        return questionRepository.findByCategory(category);
    }

    public List<Question> findByType(QuestionType type) {
        return questionRepository.findByType(type);
    }

    public List<Question> searchByText(String searchTerm) {
        return questionRepository.findByTextContainingIgnoreCase(searchTerm);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public List<Question> findAllById(List<UUID> ids) {
        return questionRepository.findAllById(ids);
    }

    public List<Question> findBySentiment(QuestionSentiment sentiment) {
        return questionRepository.findBySentiment(sentiment);
    }
} 