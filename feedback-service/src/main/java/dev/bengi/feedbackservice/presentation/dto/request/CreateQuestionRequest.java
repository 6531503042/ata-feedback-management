package dev.bengi.feedbackservice.presentation.dto.request;

import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateQuestionRequest {
    @NotBlank(message = "Text is required")
    private String text;
    
    private String content;
    
    @NotNull(message = "Category is required")
    private String category;
    
    @NotNull(message = "Type is required")
    private String type;
    
    private String answerType;
    private boolean sentimentAnalysis;
    private Boolean required = true;
    private UUID projectId;
    private List<String> options;

    public Boolean isRequired() {
        return required;
    }
} 