package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.infrastructure.persistence.adapter.QuestionPersistenceAdapter;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.QuestionMapper;
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
public class QuestionSetService {
    private final QuestionPersistenceAdapter questionPersistenceAdapter;
    private final QuestionMapper questionMapper;

    @Transactional
    public List<QuestionResponse> createQuestionSet(CreateQuestionSetRequest request) {
        List<Question> questions = questionPersistenceAdapter.findAllById(request.getQuestionIds());
        return questions.stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
    }
} 