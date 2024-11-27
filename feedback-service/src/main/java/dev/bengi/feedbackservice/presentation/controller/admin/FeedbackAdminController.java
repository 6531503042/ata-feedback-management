package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.dto.FeedbackResponse;
import dev.bengi.feedbackservice.application.dto.FeedbackSearchRequest;
import dev.bengi.feedbackservice.application.service.FeedbackService;
import dev.bengi.feedbackservice.domain.model.FeedbackSearch;
import dev.bengi.feedbackservice.domain.model.QuestionCategory;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.FeedbackMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/feedbacks")
@RequiredArgsConstructor
public class FeedbackAdminController {
    private final FeedbackService feedbackService;
    private final FeedbackMapper feedbackMapper;

    @GetMapping("/search")
    public ResponseEntity<Page<FeedbackResponse>> searchFeedbacks(
            @RequestBody FeedbackSearchRequest request,
            Pageable pageable
    ) {
        FeedbackSearch search = FeedbackSearch.builder()
                .projectId(request.getProjectId())
                .userId(request.getUserId())
                .userEmail(request.getUserEmail())
                .category(request.getCategory() != null ? 
                    QuestionCategory.valueOf(request.getCategory()) : null)
                .searchTerm(request.getSearchTerm())
                .includePrivate(true)
                .build();

        return ResponseEntity.ok(
            feedbackService.searchFeedbacks(search, pageable)
                .map(feedbackMapper::toResponse)
        );
    }
} 