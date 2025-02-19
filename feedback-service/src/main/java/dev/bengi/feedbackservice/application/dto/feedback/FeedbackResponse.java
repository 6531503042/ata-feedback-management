package dev.bengi.feedbackservice.application.dto.feedback;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponse {
    private UUID questionId;
    private String type;
    private Integer ratingValue;
    private String textValue;
} 