package dev.bengi.feedbackservice.infrastructure.persistence.mapper;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.domain.model.QuestionOption;
import dev.bengi.feedbackservice.infrastructure.persistence.entity.QuestionEntity;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionOptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.stream.Collectors;

@Slf4j
@Component
public class QuestionMapper {
    
    public Question toDomain(QuestionEntity entity) {
        if (entity == null) return null;
        return Question.builder()
                .id(entity.getId())
                .text(entity.getText())
                .category(entity.getCategory())
                .type(entity.getType())
                .build();
    }

    public QuestionResponse toResponse(Question question) {
        if (question == null) {
            return null;
        }

        try {
            return QuestionResponse.builder()
                    .id(question.getId())
                    .text(question.getText())
                    .content(question.getContent())
                    .type(question.getType() != null ? question.getType().toString() : null)
                    .category(question.getCategory() != null ? question.getCategory().toString() : null)
                    .sentimentAnalysis(question.isSentimentAnalysis())
                    .answerType(question.getAnswerType())
                    .required(question.isRequired())
                    .active(question.isActive())
                    .projectId(question.getProjectId())
                    .options(question.getOptions() != null ? 
                            question.getOptions().stream()
                                    .map(this::toOptionResponse)
                                    .collect(Collectors.toList()) : 
                            Collections.emptyList())
                    .build();
        } catch (Exception e) {
            log.error("Error mapping question to response: ", e);
            throw e;
        }
    }

    public QuestionOptionResponse toOptionResponse(QuestionOption option) {
        if (option == null) {
            return null;
        }

        return QuestionOptionResponse.builder()
                .text(option.getText())
                .value(option.getValue())
                .build();
    }

    public QuestionEntity toEntity(Question question) {
        if (question == null) return null;
        return QuestionEntity.builder()
                .id(question.getId())
                .text(question.getText())
                .category(question.getCategory())
                .type(question.getType())
                .build();
    }
} 