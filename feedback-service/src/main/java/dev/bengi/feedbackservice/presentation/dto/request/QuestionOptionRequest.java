package dev.bengi.feedbackservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class QuestionOptionRequest {
    @NotBlank(message = "Option text is required")
    private String text;
    
    @NotNull(message = "Option value is required")
    private Integer value;
}