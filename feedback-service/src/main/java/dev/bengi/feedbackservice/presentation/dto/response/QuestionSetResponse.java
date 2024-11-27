package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class QuestionSetResponse {
    private UUID id;
    private String name;
    private String description;
    private List<UUID> questionIds;
} 