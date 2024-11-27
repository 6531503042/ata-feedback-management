package dev.bengi.feedbackservice.application.dto;

import dev.bengi.feedbackservice.domain.model.QuestionCategory;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class FeedbackSearch {
    private UUID projectId;
    private UUID userId;
    private String userEmail;
    private String category;
    private List<String> categories;
    private String searchTerm;
    private boolean includePrivate;
} 