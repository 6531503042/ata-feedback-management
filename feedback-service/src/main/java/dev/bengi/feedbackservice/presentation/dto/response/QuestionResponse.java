package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class QuestionResponse {
    private UUID id;
    private String text;
    private String content;
    private String type;
    private String category;
    private String answerType;
    private boolean sentimentAnalysis;
    private boolean required;
    private boolean active;
    private UUID projectId;
    private List<QuestionOptionResponse> options;
} 