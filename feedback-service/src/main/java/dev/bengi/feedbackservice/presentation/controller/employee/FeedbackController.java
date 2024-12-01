package dev.bengi.feedbackservice.presentation.controller.employee;

import dev.bengi.feedbackservice.application.dto.feedback.SubmitFeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.request.FeedbackRequest;
import dev.bengi.feedbackservice.presentation.dto.request.FeedbackResponseRequest;
import dev.bengi.feedbackservice.presentation.dto.response.FeedbackResponse;
import dev.bengi.feedbackservice.application.service.FeedbackService;
import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.FeedbackMapper;
import dev.bengi.feedbackservice.security.CurrentUser;
import dev.bengi.feedbackservice.security.CurrentUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @PostMapping
    public ResponseEntity<FeedbackResponse> submitFeedback(
            @CurrentUser CurrentUserDetails currentUser,
            @RequestBody SubmitFeedbackRequest request
    ) {
        FeedbackRequest feedbackRequest = FeedbackRequest.builder()
                .projectId(request.getProjectId())
                .questionSetId(request.getQuestionSetId())
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .privacyLevel(request.getPrivacyLevel())
                .rating(request.getRating())
                .additionalComments(request.getAdditionalComments())
                .submittedAt(request.getSubmittedAt())
                .responses(request.getResponses().stream()
                        .map(response -> new FeedbackResponseRequest(response.getType(), response.getRatingValue()))
                        .collect(Collectors.toList()))
                .build();

        Feedback feedback = feedbackService.submitFeedback(currentUser.getId(), feedbackRequest);
        return ResponseEntity.ok(feedbackMapper.toResponse(feedback));
    }

    @GetMapping("/my-feedbacks")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<FeedbackResponse>> getMyFeedbacks(
            @CurrentUser CurrentUserDetails currentUser,
            @RequestParam(required = false) UUID projectId
    ) {
        List<Feedback> feedbacks = projectId != null ?
                feedbackService.getUserFeedbacksByProject(currentUser.getId(), projectId) :
                feedbackService.getUserFeedbacks(currentUser.getId());
        
        List<FeedbackResponse> responses = feedbacks.stream()
                .map(feedbackMapper::toResponse)
                .collect(Collectors.toList());
                
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/my-feedbacks/{feedbackId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<FeedbackResponse> getMyFeedbackById(
            @CurrentUser CurrentUserDetails currentUser,
            @PathVariable UUID feedbackId
    ) {
        Feedback feedback = feedbackService.getUserFeedbackById(currentUser.getId(), feedbackId);
        return ResponseEntity.ok(feedbackMapper.toResponse(feedback));
    }

    @GetMapping("/projects/{projectId}/question-sets/{setId}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<List<FeedbackResponse>> getFeedbacksByQuestionSet(
            @PathVariable UUID projectId,
            @PathVariable UUID setId
    ) {
        List<Feedback> feedbacks = feedbackService.getFeedbacksByQuestionSet(projectId, setId);
        List<FeedbackResponse> responses = feedbacks.stream()
                .map(feedbackMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }
}