package dev.bengi.feedbackservice.presentation.dto.response;

import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class QuestionResponse {
    private UUID id;
    private String text;
    private QuestionType type;
    private QuestionCategory category;
    private QuestionSentiment sentiment;
    private UUID projectId;
} 