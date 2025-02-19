package dev.bengi.feedbackservice.application.dto.feedback;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackSearch {
    private UUID projectId;
    private UUID userId;
    private String userEmail;
    private QuestionCategory category;
    private List<QuestionCategory> categories;
    private String searchTerm;
    private boolean includePrivate;
} 