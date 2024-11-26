package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.QuestionSetUseCase;
import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.QuestionSet;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.QuestionRepository;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.QuestionSetRepository;
import dev.bengi.feedbackservice.presentation.dto.request.AssignQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionSetService implements QuestionSetUseCase {
    private final QuestionRepository questionRepository;
    private final QuestionSetRepository questionSetRepository;

    @Override
    @Transactional
    public QuestionSetResponse createQuestionSet(CreateQuestionSetRequest request) {
        List<Question> questions = questionRepository.findAllById(request.getQuestionIds());
        
        QuestionSet questionSet = QuestionSet.builder()
                .name(request.getName())
                .description(request.getDescription())
                .questions(questions)
                .active(true)
                .build();

        QuestionSet savedQuestionSet = questionSetRepository.save(questionSet);
        return mapToResponse(savedQuestionSet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionSetResponse> getAllQuestionSets() {
        return questionSetRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public QuestionSetResponse toggleQuestionSetStatus(UUID setId, boolean active) {
        QuestionSet questionSet = questionSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Question set not found"));
        
        questionSet.setActive(active);
        QuestionSet updatedSet = questionSetRepository.save(questionSet);
        return mapToResponse(updatedSet);
    }

    @Override
    @Transactional
    public QuestionSetResponse assignToProject(UUID setId, AssignQuestionSetRequest request) {
        QuestionSet questionSet = questionSetRepository.findById(setId)
                .orElseThrow(() -> new RuntimeException("Question set not found"));
        
        questionSet.setProjectId(request.getProjectId());
        questionSet.setStartDate(request.getStartDate());
        questionSet.setEndDate(request.getEndDate());
        
        QuestionSet updatedSet = questionSetRepository.save(questionSet);
        return mapToResponse(updatedSet);
    }

    private QuestionSetResponse mapToResponse(QuestionSet questionSet) {
        return QuestionSetResponse.builder()
                .id(questionSet.getId())
                .name(questionSet.getName())
                .description(questionSet.getDescription())
                .active(questionSet.isActive())
                .questions(questionSet.getQuestions().stream()
                        .map(this::mapQuestionToResponse)
                        .collect(Collectors.toList()))
                .startDate(questionSet.getStartDate())
                .endDate(questionSet.getEndDate())
                .projectId(questionSet.getProjectId())
                .build();
    }

    private QuestionResponse mapQuestionToResponse(Question question) {
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