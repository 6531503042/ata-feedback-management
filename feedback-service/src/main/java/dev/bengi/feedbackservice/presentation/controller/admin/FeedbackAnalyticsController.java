package dev.bengi.feedbackservice.presentation.controller.admin;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectMetricsResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class FeedbackAnalyticsController {
    private final FeedbackUseCase feedbackUseCase;

    @GetMapping("/feedback")
    public ResponseEntity<ApiResponse<List<Feedback>>> getFeedbackWithFilters(
            @RequestParam(required = false) UUID projectId,
            @RequestParam(required = false) QuestionCategory category,
            @RequestParam(required = false) QuestionSentiment sentiment,
            @RequestParam(required = false) String privacyLevel,
            @RequestParam(required = false) String searchTerm) {
        return ResponseEntity.ok(ApiResponse.success(
            feedbackUseCase.getFeedbacksWithFilters(projectId, category, sentiment, privacyLevel, searchTerm)
        ));
    }

    @GetMapping("/metrics/project/{projectId}")
    public ResponseEntity<ProjectMetricsResponse> getProjectMetrics(@PathVariable UUID projectId) {
        return ResponseEntity.ok(feedbackUseCase.getProjectMetrics(projectId));
    }

    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<Feedback>>> getRecentFeedback(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.success(
            feedbackUseCase.getRecentFeedback(limit)
        ));
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<byte[]> generateYearlyReport(@PathVariable int year) {
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=yearly-report-" + year + ".pdf")
                .body(feedbackUseCase.generateYearlyReport(year));
    }
} 