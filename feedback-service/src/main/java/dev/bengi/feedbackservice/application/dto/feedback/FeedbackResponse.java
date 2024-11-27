package dev.bengi.feedbackservice.application.dto.feedback;

import lombok.Data;
import java.util.UUID;

@Data
public class FeedbackResponse {
    private UUID questionId;
    private String type;
    private Integer ratingValue;
    private String textValue;
} 