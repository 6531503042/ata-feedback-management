package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.port.input.QuestionUseCase;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/questions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class QuestionController {
    private final QuestionUseCase questionUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(
            @RequestBody @Valid CreateQuestionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(questionUseCase.createQuestion(request)));
    }

    @PostMapping("/set")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> createQuestionSet(
            @RequestBody @Valid CreateQuestionSetRequest request) {
        return ResponseEntity.ok(ApiResponse.success(questionUseCase.createQuestionSet(request)));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuestionsByCategory(
            @PathVariable QuestionCategory category) {
        return ResponseEntity.ok(ApiResponse.success(questionUseCase.getQuestionsByCategory(category)));
    }

    @GetMapping("/sentiment/{sentiment}")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuestionsBySentiment(
            @PathVariable QuestionSentiment sentiment) {
        return ResponseEntity.ok(ApiResponse.success(questionUseCase.getQuestionsBySentiment(sentiment)));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> updateQuestion(
            @PathVariable UUID questionId,
            @RequestBody @Valid CreateQuestionRequest request) {
        return ResponseEntity.ok(ApiResponse.success(questionUseCase.updateQuestion(questionId, request)));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable UUID questionId) {
        questionUseCase.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }
} 