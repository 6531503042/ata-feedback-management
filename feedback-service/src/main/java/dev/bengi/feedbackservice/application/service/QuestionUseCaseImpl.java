package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.QuestionUseCase;
import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.QuestionPersistenceAdapter;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.UpdateQuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QuestionUseCaseImpl implements QuestionUseCase {
    private final QuestionPersistenceAdapter questionPersistenceAdapter;

    @Override
    public Question createQuestion(CreateQuestionRequest request) {
        Question question = Question.builder()
                .text(request.getText())
                .content(request.getContent())
                .type(QuestionType.valueOf(request.getType()))
                .category(QuestionCategory.valueOf(request.getCategory()))
                .answerType(request.getAnswerType())
                .required(request.getRequired())
                .active(true)
                .build();
        
        return questionPersistenceAdapter.save(question);
    }

    @Override
    public Question updateQuestion(UUID id, UpdateQuestionRequest request) {
        Question question = questionPersistenceAdapter.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setText(request.getText());
        question.setType(QuestionType.valueOf(request.getType()));
        question.setCategory(QuestionCategory.valueOf(request.getCategory()));
        
        return questionPersistenceAdapter.save(question);
    }

    @Override
    public void deleteQuestion(UUID id) {
        questionPersistenceAdapter.deleteById(id);
    }

    @Override
    public Question getQuestionById(UUID id) {
        return questionPersistenceAdapter.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found"));
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionPersistenceAdapter.findAll();
    }

    @Override
    public List<Question> getQuestionsByType(QuestionType type) {
        return questionPersistenceAdapter.findByType(type);
    }

    @Override
    public List<Question> getQuestionsByCategory(QuestionCategory category) {
        return questionPersistenceAdapter.findByCategory(category);
    }

    @Override
    public List<Question> getQuestionsBySentimentAnalysis(boolean sentimentAnalysis) {
        return questionPersistenceAdapter.findBySentimentAnalysis(sentimentAnalysis);
    }

    @Override
    public List<Question> getQuestionsByProject(UUID projectId) {
        return questionPersistenceAdapter.findByProjectId(projectId);
    }

    @Override
    public List<Question> searchQuestions(String searchTerm) {
        return questionPersistenceAdapter.findByTextContainingIgnoreCase(searchTerm);
    }
} 