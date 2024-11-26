package dev.bengi.feedbackservice.application.port.input;

import dev.bengi.feedbackservice.presentation.dto.request.AssignQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;

import java.util.List;
import java.util.UUID;

public interface QuestionSetUseCase {
    QuestionSetResponse createQuestionSet(CreateQuestionSetRequest request);
    List<QuestionSetResponse> getAllQuestionSets();
    QuestionSetResponse toggleQuestionSetStatus(UUID setId, boolean active);
    QuestionSetResponse assignToProject(UUID setId, AssignQuestionSetRequest request);
} 