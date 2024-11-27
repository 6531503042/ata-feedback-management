package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.port.input.QuestionUseCase;
import dev.bengi.feedbackservice.application.port.input.QuestionSetUseCase;
import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.UpdateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/questions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class QuestionController {
    private final QuestionUseCase questionUseCase;
    private final QuestionSetUseCase questionSetUseCase;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getAllQuestions() {
        List<Question> questions = questionUseCase.getAllQuestions();
        List<QuestionResponse> responses = questions.stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(@Valid @RequestBody CreateQuestionRequest request) {
        Question question = questionUseCase.createQuestion(request);
        return ResponseEntity.ok(ApiResponse.success(mapToQuestionResponse(question)));
    }

    @PostMapping("/set")
    public ResponseEntity<ApiResponse<QuestionSetResponse>> createQuestionSet(
            @RequestBody @Valid CreateQuestionSetRequest request) {
        QuestionSetResponse response = questionSetUseCase.createQuestionSet(request);
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuestionsByCategory(@PathVariable QuestionCategory category) {
        List<Question> questions = questionUseCase.getQuestionsByCategory(category);
        List<QuestionResponse> responses = questions.stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/sentiment/{sentiment}")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuestionsBySentiment(
            @PathVariable QuestionSentiment sentiment) {
        List<Question> questions = questionUseCase.getQuestionsBySentiment(sentiment);
        List<QuestionResponse> responses = questions.stream()
                .map(this::mapToQuestionResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> updateQuestion(
            @PathVariable UUID questionId,
            @Valid @RequestBody UpdateQuestionRequest request) {
        Question updatedQuestion = questionUseCase.updateQuestion(questionId, request);
        return ResponseEntity.ok(ApiResponse.success(mapToQuestionResponse(updatedQuestion)));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable UUID questionId) {
        questionUseCase.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    private QuestionResponse mapToQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .text(question.getText())
                .type(question.getType())
                .category(question.getCategory())
                .sentiment(question.getSentiment())
                .projectId(question.getProject() != null ? question.getProject().getId() : null)
                .build();
    }
} 