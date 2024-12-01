package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.service.QuestionService;
import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.QuestionMapper;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.request.CreateQuestionSetRequest;
import dev.bengi.feedbackservice.presentation.dto.request.UpdateQuestionRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionSetResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/admin/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getAllQuestions() {
        try {
            log.info("Fetching all questions");
            List<Question> questions = questionService.getAllQuestions();
            List<QuestionResponse> responses = questions.stream()
                    .map(questionMapper::toResponse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(ApiResponse.success(responses));
        } catch (Exception e) {
            log.error("Error fetching all questions: ", e);
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<QuestionResponse>> createQuestion(@Valid @RequestBody CreateQuestionRequest request) {
        try {
            log.info("Creating question with request: {}", request);
            Question question = questionService.createQuestion(request);
            QuestionResponse response = questionMapper.toResponse(question);
            return ResponseEntity.ok(ApiResponse.success(response));
        } catch (Exception e) {
            log.error("Error creating question: ", e);
            throw e;
        }
    }

    @PostMapping("/question-sets")
    public ResponseEntity<ApiResponse<QuestionSetResponse>> createQuestionSet(
            @Valid @RequestBody CreateQuestionSetRequest request
    ) {
        List<QuestionResponse> responses = questionService.createQuestionSet(request);
        return ResponseEntity.ok(ApiResponse.success(mapToQuestionSetResponse(responses)));
    }

    @GetMapping("/categories/{category}")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuestionsByCategory(@PathVariable QuestionCategory category) {
        List<Question> questions = questionService.getQuestionsByCategory(category);
        List<QuestionResponse> responses = questions.stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @GetMapping("/sentiment-analysis/{enabled}")
    public ResponseEntity<ApiResponse<List<QuestionResponse>>> getQuestionsBySentimentAnalysis(
            @PathVariable boolean enabled
    ) {
        List<Question> questions = questionService.getQuestionsBySentimentAnalysis(enabled);
        List<QuestionResponse> responses = questions.stream()
                .map(questionMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(responses));
    }

    @PutMapping("/{questionId}")
    public ResponseEntity<ApiResponse<QuestionResponse>> updateQuestion(
            @PathVariable UUID questionId,
            @Valid @RequestBody UpdateQuestionRequest request
    ) {
        Question question = questionService.getQuestionById(questionId);
        QuestionResponse response = QuestionResponse.builder()
                .id(question.getId())
                .text(question.getText())
                .content(question.getContent())
                .type(question.getType().name())
                .category(question.getCategory().name())
                .sentimentAnalysis(question.isSentimentAnalysis())
                .answerType(question.getAnswerType())
                .required(question.isRequired())
                .active(question.isActive())
                .projectId(question.getProjectId())
                .options(question.getOptions().stream()
                        .map(questionMapper::toOptionResponse)
                        .collect(Collectors.toList()))
                .build();
        return ResponseEntity.ok(ApiResponse.success(response));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<ApiResponse<Void>> deleteQuestion(@PathVariable UUID questionId) {
        questionService.deleteQuestion(questionId);
        return ResponseEntity.ok(ApiResponse.success(null));
    }

    private QuestionSetResponse mapToQuestionSetResponse(List<QuestionResponse> questions) {
        return QuestionSetResponse.builder()
                .questionIds(questions.stream()
                        .map(QuestionResponse::getId)
                        .collect(Collectors.toList()))
                .build();
    }
} 