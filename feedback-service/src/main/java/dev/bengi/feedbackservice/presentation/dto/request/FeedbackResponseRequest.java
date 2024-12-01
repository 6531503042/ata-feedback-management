package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackResponseRequest {
    private String type;
    private Integer ratingValue;
} 