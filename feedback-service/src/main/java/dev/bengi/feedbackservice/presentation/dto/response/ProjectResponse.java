package dev.bengi.feedbackservice.presentation.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectResponse {
    private UUID id;
    private String name;
    private String description;
    private boolean active;
    private LocalDateTime feedbackStartDate;
    private LocalDateTime feedbackEndDate;
    private Integer totalEmployees;
    private Integer participatedEmployees;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}