package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestionOptionResponse {
    private String text;
    private Integer value;
} 