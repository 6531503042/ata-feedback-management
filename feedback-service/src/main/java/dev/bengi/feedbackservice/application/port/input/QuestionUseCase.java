package dev.bengi.feedbackservice.application.port.input;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionUseCase {
    QuestionResponse createQuestion(CreateQuestionRequest request);
    List<QuestionResponse> createQuestionSet(CreateQuestionSetRequest request);
    List<QuestionResponse> getQuestionsByCategory(QuestionCategory category);
    List<QuestionResponse> getQuestionsBySentiment(QuestionSentiment sentiment);
    QuestionResponse updateQuestion(UUID questionId, CreateQuestionRequest request);
    void deleteQuestion(UUID questionId);
} 