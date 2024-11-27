package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.QuestionMapper;
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
public class QuestionService {
    private final QuestionRepository questionRepository;
    private final QuestionMapper questionMapper;

    @Transactional
    public QuestionResponse createQuestion(CreateQuestionRequest request) {
        Question question = Question.builder()
                .id(UUID.randomUUID())
                .text(request.getText())
                .category(QuestionCategory.valueOf(request.getCategory()))
                .type(QuestionType.valueOf(request.getType()))
                .build();

        Question savedQuestion = questionRepository.save(question);
        return questionMapper.toResponse(savedQuestion);
    }

    @Transactional(readOnly = true)
    public List<QuestionResponse> getQuestionSet(UUID setId) {
        List<Question> questions = questionRepository.findByQuestionSetId(setId);
        return questions.stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<QuestionResponse> createQuestionSet(CreateQuestionSetRequest request) {
        List<Question> questions = questionRepository.findAllById(request.getQuestionIds());
        return questions.stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }
} 