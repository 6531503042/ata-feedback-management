package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.QuestionOption;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.QuestionMapper;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.QuestionRepository;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import dev.bengi.feedbackservice.domain.exception.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Transactional(readOnly = true)
    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    @Transactional
    public Question createQuestion(CreateQuestionRequest request) {
        log.info("Creating question with request: {}", request);
        try {
            Question question = Question.builder()
                    .text(request.getText())
                    .content(request.getContent())
                    .type(QuestionType.valueOf(request.getType()))
                    .category(QuestionCategory.valueOf(request.getCategory()))
                    .answerType(request.getAnswerType())
                    .required(request.isRequired())
                    .active(true)
                    .build();

            // Add options if they exist
            if (request.getOptions() != null) {
                request.getOptions().forEach(optionText -> {
                    QuestionOption option = QuestionOption.builder()
                            .text(optionText)
                            .build();
                    question.addOption(option);
                });
            }

            Question savedQuestion = questionRepository.save(question);
            log.info("Successfully created question with ID: {}", savedQuestion.getId());
            return savedQuestion;
        } catch (Exception e) {
            log.error("Error creating question: ", e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> createQuestionSet(CreateQuestionSetRequest request) {
        return questionRepository.findAllById(request.getQuestionIds()).stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsByCategory(QuestionCategory category) {
        return questionRepository.findByCategory(category);
    }

    @Transactional(readOnly = true)
    public List<Question> getQuestionsBySentimentAnalysis(boolean sentimentAnalysis) {
        return questionRepository.findBySentimentAnalysis(sentimentAnalysis);
    }

    @Transactional(readOnly = true)
    public Question getQuestionById(UUID questionId) {
        return questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
    }

    @Transactional
    public void deleteQuestion(UUID questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));
        questionRepository.delete(question);
    }
} 