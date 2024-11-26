package dev.bengi.feedbackservice.presentation.dto.response;

import dev.bengi.feedbackservice.domain.model.enums.AnswerType;
import dev.bengi.feedbackservice.domain.model.enums.QuestionCategory;
import dev.bengi.feedbackservice.domain.model.enums.QuestionSentiment;
import dev.bengi.feedbackservice.domain.model.enums.QuestionType;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class QuestionResponse {
    private UUID id;
    private String content;
    private QuestionCategory category;
    private QuestionType type;
    private AnswerType answerType;
    private QuestionSentiment sentiment;
    private List<String> options;
    private boolean required;
    private boolean active;
    private String wordBasedPrompt;
} 