package dev.bengi.feedbackservice.presentation.dto.request;

import dev.bengi.feedbackservice.domain.model.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateQuestionRequest {
    @NotBlank(message = "Question content is required")
    private String content;

    @NotNull(message = "Question category is required")
    private QuestionCategory category;

    @NotNull(message = "Question type is required")
    private QuestionType type;

    private String[] options; // For multiple choice questions
    private boolean required;
}