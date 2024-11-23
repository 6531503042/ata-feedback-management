package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackAnalyticsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasAnyRole('ADMIN', 'HR')")
public class AnalyticsController {
    private final FeedbackUseCase feedbackUseCase;

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<FeedbackAnalyticsResponse> getProjectAnalytics(@PathVariable UUID projectId) {
        return ResponseEntity.ok(feedbackUseCase.getProjectAnalytics(projectId));
    }

    @GetMapping("/yearly/{year}")
    public ResponseEntity<FeedbackAnalyticsResponse> getYearlyAnalytics(@PathVariable int year) {
        return ResponseEntity.ok(feedbackUseCase.getYearlyAnalytics(year));
    }

    @GetMapping("/export/yearly/{year}")
    public ResponseEntity<byte[]> exportYearlyReport(@PathVariable int year) {
        byte[] report = feedbackUseCase.generateYearlyReport(year);
        return ResponseEntity.ok()
                .header("Content-Type", "application/pdf")
                .header("Content-Disposition", "attachment; filename=yearly-report-" + year + ".pdf")
                .body(report);
    }
}