package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.QuestionSetUseCase;
import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.QuestionSet;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.QuestionPersistenceAdapter;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.QuestionSetPersistenceAdapter;
import dev.bengi.feedbackservice.presentation.dto.request.AssignQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionSetUseCaseImpl implements QuestionSetUseCase {
    private final QuestionSetPersistenceAdapter questionSetPersistenceAdapter;
    private final QuestionPersistenceAdapter questionPersistenceAdapter;

    @Override
    @Transactional
    public QuestionSetResponse createQuestionSet(CreateQuestionSetRequest request) {
        QuestionSet questionSet = QuestionSet.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .title(request.getTitle())
                .description(request.getDescription())
                .active(true)
                .projectId(request.getProjectId())
                .build();

        List<Question> questions = questionPersistenceAdapter.findAllById(request.getQuestionIds());
        questions.forEach(questionSet::addQuestion);

        QuestionSet savedQuestionSet = questionSetPersistenceAdapter.save(questionSet);
        return mapToResponse(savedQuestionSet);
    }

    @Override
    @Transactional(readOnly = true)
    public QuestionSetResponse getQuestionSet(UUID id) {
        QuestionSet questionSet = questionSetPersistenceAdapter.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionSet not found"));
        return mapToResponse(questionSet);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuestionSetResponse> getAllQuestionSets() {
        return questionSetPersistenceAdapter.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteQuestionSet(UUID id) {
        questionSetPersistenceAdapter.deleteById(id);
    }

    @Override
    @Transactional
    public QuestionSetResponse updateQuestionSet(UUID id, CreateQuestionSetRequest request) {
        QuestionSet questionSet = questionSetPersistenceAdapter.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionSet not found"));

        questionSet.setName(request.getName());
        questionSet.setTitle(request.getTitle());
        questionSet.setDescription(request.getDescription());
        questionSet.getQuestions().clear();

        List<Question> questions = questionPersistenceAdapter.findAllById(request.getQuestionIds());
        questions.forEach(questionSet::addQuestion);

        QuestionSet updatedQuestionSet = questionSetPersistenceAdapter.save(questionSet);
        return mapToResponse(updatedQuestionSet);
    }

    @Override
    @Transactional
    public QuestionSetResponse toggleQuestionSetStatus(UUID id, boolean active) {
        QuestionSet questionSet = questionSetPersistenceAdapter.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionSet not found"));
        
        questionSet.setActive(active);
        QuestionSet updatedQuestionSet = questionSetPersistenceAdapter.save(questionSet);
        return mapToResponse(updatedQuestionSet);
    }

    @Override
    @Transactional
    public QuestionSetResponse assignToProject(UUID id, AssignQuestionSetRequest request) {
        QuestionSet questionSet = questionSetPersistenceAdapter.findById(id)
                .orElseThrow(() -> new RuntimeException("QuestionSet not found"));
        
        questionSet.setProjectId(request.getProjectId());
        QuestionSet updatedQuestionSet = questionSetPersistenceAdapter.save(questionSet);
        return mapToResponse(updatedQuestionSet);
    }

    private QuestionSetResponse mapToResponse(QuestionSet questionSet) {
        return QuestionSetResponse.builder()
                .id(questionSet.getId())
                .name(questionSet.getName())
                .title(questionSet.getTitle())
                .description(questionSet.getDescription())
                .active(questionSet.isActive())
                .projectId(questionSet.getProjectId())
                .questionIds(questionSet.getQuestions().stream()
                        .map(Question::getId)
                        .collect(Collectors.toList()))
                .build();
    }
} 