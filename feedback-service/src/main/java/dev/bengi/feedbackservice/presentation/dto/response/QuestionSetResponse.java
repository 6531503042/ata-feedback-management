package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionSetResponse {
    private UUID id;
    private String name;
    private String title;
    private String description;
    private boolean active;
    private UUID projectId;
    private List<UUID> questionIds;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 