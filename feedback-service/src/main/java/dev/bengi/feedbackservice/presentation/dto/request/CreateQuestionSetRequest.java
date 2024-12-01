package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class CreateQuestionSetRequest {
    private String name;
    private String title;
    private String description;
    private UUID projectId;
    private List<UUID> questionIds;
} 