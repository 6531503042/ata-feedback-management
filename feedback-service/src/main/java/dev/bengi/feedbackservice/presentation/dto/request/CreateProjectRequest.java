package dev.bengi.feedbackservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectRequest {
    @NotBlank(message = "Project name is required")
    private String name;

    private String description;

    @NotNull(message = "Feedback start date is required")
    private LocalDateTime feedbackStartDate;

    @NotNull(message = "Feedback end date is required")
    private LocalDateTime feedbackEndDate;

    @NotNull(message = "Total employees is required")
    @Min(value = 1, message = "Total employees must be at least 1")
    private Integer totalEmployees;
}