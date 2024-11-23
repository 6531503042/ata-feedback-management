package dev.bengi.feedbackservice.presentation.controller.employee;

import dev.bengi.feedbackservice.application.port.input.FeedbackUseCase;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.presentation.dto.request.SubmitFeedbackRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/feedback")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackUseCase feedbackUseCase;

    @PostMapping("/submit")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Feedback> submitFeedback(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody SubmitFeedbackRequest request) {
        return ResponseEntity.ok(feedbackUseCase.submitFeedback(
                UUID.fromString(userDetails.getUsername()),
                request));
    }
}