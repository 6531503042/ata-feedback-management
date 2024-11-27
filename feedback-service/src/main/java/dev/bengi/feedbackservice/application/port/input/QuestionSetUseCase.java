package dev.bengi.feedbackservice.application.port.input;

import dev.bengi.feedbackservice.presentation.dto.request.AssignQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionSetUseCase {
    QuestionSetResponse createQuestionSet(CreateQuestionSetRequest request);
    QuestionSetResponse getQuestionSet(UUID id);
    List<QuestionSetResponse> getAllQuestionSets();
    void deleteQuestionSet(UUID id);
    QuestionSetResponse updateQuestionSet(UUID id, CreateQuestionSetRequest request);
    QuestionSetResponse toggleQuestionSetStatus(UUID id, boolean active);
    QuestionSetResponse assignToProject(UUID id, AssignQuestionSetRequest request);
} 