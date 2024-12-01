package dev.bengi.feedbackservice.infrastructure.mapper;

import dev.bengi.feedbackservice.domain.model.Project;
import dev.bengi.feedbackservice.presentation.dto.request.CreateProjectRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectResponse;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ProjectMapper {
    public Project toProject(CreateProjectRequest request) {
        return Project.builder()
                .id(UUID.randomUUID())
                .name(request.getName())
                .description(request.getDescription())
                .feedbackStartDate(request.getFeedbackStartDate())
                .feedbackEndDate(request.getFeedbackEndDate())
                .totalEmployees(request.getTotalEmployees())
                .active(true)
                .build();
    }

    public ProjectResponse toProjectResponse(Project project) {
        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .feedbackStartDate(project.getFeedbackStartDate())
                .feedbackEndDate(project.getFeedbackEndDate())
                .totalEmployees(project.getTotalEmployees())
                .participatedEmployees(project.getParticipatedEmployees())
                .active(project.isActive())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .build();
    }
}