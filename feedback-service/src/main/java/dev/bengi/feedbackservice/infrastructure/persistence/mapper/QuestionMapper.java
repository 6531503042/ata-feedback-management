package dev.bengi.feedbackservice.infrastructure.persistence.mapper;

import dev.bengi.feedbackservice.domain.model.Question;
import dev.bengi.feedbackservice.infrastructure.persistence.entity.QuestionEntity;
import dev.bengi.feedbackservice.presentation.dto.response.QuestionResponse;
import org.springframework.stereotype.Component;

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
        if (question == null) return null;
        return QuestionResponse.builder()
                .id(question.getId())
                .text(question.getText())
                .category(question.getCategory())
                .type(question.getType())
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