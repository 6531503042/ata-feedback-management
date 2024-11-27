package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CreateQuestionSetRequest {
    private String name;
    private String description;
    private List<UUID> questionIds;
} 