package dev.bengi.feedbackservice.domain.model;

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
    private QuestionCategory category;
    private List<QuestionCategory> categories;
    private String searchTerm;
    private boolean includePrivate;
} 