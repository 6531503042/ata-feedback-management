package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.QuestionUseCase;
import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.QuestionRepository;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionService implements QuestionUseCase {
    private final QuestionRepository questionRepository;

    @Override
    @Transactional
    public QuestionResponse createQuestion(CreateQuestionRequest request) {
        Question question = Question.builder()
                .content(request.getContent())
                .category(request.getCategory())
                .type(request.getType())
                .answerType(request.getAnswerType())
                .options(request.getOptions() != null ? List.of(request.getOptions()) : null)
                .required(request.isRequired())
                .wordBasedPrompt(request.getWordBasedPrompt())
                .active(true)
                .build();

        Question savedQuestion = questionRepository.save(question);
        return mapToResponse(savedQuestion);
    }

    @Override
    @Transactional
    public List<QuestionResponse> createQuestionSet(CreateQuestionSetRequest request) {
        List<Question> questions = questionRepository.findAllById(request.getQuestionIds());
        return questions.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionsByCategory(QuestionCategory category) {
        return questionRepository.findByCategory(category).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionsBySentiment(QuestionSentiment sentiment) {
        return questionRepository.findBySentiment(sentiment).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionResponse updateQuestion(UUID questionId, CreateQuestionRequest request) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found"));

        question.setContent(request.getContent());
        question.setCategory(request.getCategory());
        question.setType(request.getType());
        question.setAnswerType(request.getAnswerType());
        question.setOptions(request.getOptions() != null ? List.of(request.getOptions()) : null);
        question.setRequired(request.isRequired());
        question.setWordBasedPrompt(request.getWordBasedPrompt());

        Question updatedQuestion = questionRepository.save(question);
        return mapToResponse(updatedQuestion);
    }

    @Override
    @Transactional
    public void deleteQuestion(UUID questionId) {
        questionRepository.deleteById(questionId);
    }

    private QuestionResponse mapToResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .content(question.getContent())
                .category(question.getCategory())
                .type(question.getType())
                .answerType(question.getAnswerType())
                .sentiment(question.getSentiment())
                .options(question.getOptions())
                .required(question.isRequired())
                .active(question.isActive())
                .wordBasedPrompt(question.getWordBasedPrompt())
                .build();
    }
} 