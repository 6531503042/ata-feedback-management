package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.Data;

@Data
public class UpdateQuestionRequest {
    private String text;
    private String type;
    private String category;
    private String sentiment;
    private String answerType;
} 