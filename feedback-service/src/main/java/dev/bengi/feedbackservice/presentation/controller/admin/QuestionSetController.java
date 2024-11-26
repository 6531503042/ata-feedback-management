package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.port.input.QuestionSetUseCase;
import dev.bengi.feedbackservice.presentation.dto.request.AssignQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/question-sets")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class QuestionSetController {
    private final QuestionSetUseCase questionSetUseCase;

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionSetResponse>> createQuestionSet(
            @RequestBody @Valid CreateQuestionSetRequest request) {
        return ResponseEntity.ok(ApiResponse.success(questionSetUseCase.createQuestionSet(request)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionSetResponse>>> getAllQuestionSets() {
        return ResponseEntity.ok(ApiResponse.success(questionSetUseCase.getAllQuestionSets()));
    }

    @PutMapping("/{setId}/status")
    public ResponseEntity<ApiResponse<QuestionSetResponse>> toggleQuestionSetStatus(
            @PathVariable UUID setId,
            @RequestParam boolean active) {
        return ResponseEntity.ok(ApiResponse.success(questionSetUseCase.toggleQuestionSetStatus(setId, active)));
    }

    @PostMapping("/{setId}/assign")
    public ResponseEntity<ApiResponse<QuestionSetResponse>> assignToProject(
            @PathVariable UUID setId,
            @RequestBody @Valid AssignQuestionSetRequest request) {
        return ResponseEntity.ok(ApiResponse.success(questionSetUseCase.assignToProject(setId, request)));
    }
} 