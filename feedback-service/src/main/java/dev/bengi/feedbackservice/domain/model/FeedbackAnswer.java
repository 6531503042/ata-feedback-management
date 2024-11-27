package dev.bengi.feedbackservice.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAnswer {
    private String type;
    private Integer ratingValue;
    private String textValue;
} 