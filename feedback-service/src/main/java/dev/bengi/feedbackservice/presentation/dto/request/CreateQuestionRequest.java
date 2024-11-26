package dev.bengi.feedbackservice.presentation.dto.request;

import dev.bengi.feedbackservice.domain.model.enums.AnswerType;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
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

    @NotNull(message = "Answer type is required")
    private AnswerType answerType;

    private String[] options;  // For satisfaction ratings

    private String wordBasedPrompt;  // For word-based questions

    private boolean required = true;
}