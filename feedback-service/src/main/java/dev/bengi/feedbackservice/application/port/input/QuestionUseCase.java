package dev.bengi.feedbackservice.application.port.input;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.UpdateQuestionRequest;

import java.util.List;
import java.util.UUID;

public interface QuestionUseCase {
    Question createQuestion(CreateQuestionRequest request);
    Question updateQuestion(UUID id, UpdateQuestionRequest request);
    void deleteQuestion(UUID id);
    Question getQuestionById(UUID id);
    List<Question> getQuestionsByProject(UUID projectId);
    List<Question> getQuestionsByCategory(QuestionCategory category);
    List<Question> getQuestionsByType(QuestionType type);
    List<Question> searchQuestions(String searchTerm);
    List<Question> getAllQuestions();
    List<Question> getQuestionsBySentiment(QuestionSentiment sentiment);
} 