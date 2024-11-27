package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.Data;
import java.util.UUID;

@Data
public class FeedbackSearchRequest {
    private UUID projectId;
    private UUID userId;
    private String userEmail;
    private String category;
    private String searchTerm;
} 