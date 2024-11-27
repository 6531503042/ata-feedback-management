package dev.bengi.feedbackservice.application.dto.feedback;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAnswerDto {
    private UUID questionId;
    private String type;
    private Integer ratingValue;
    private String textValue;
} 