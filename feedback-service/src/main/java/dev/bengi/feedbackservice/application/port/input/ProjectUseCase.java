package dev.bengi.feedbackservice.application.port.input;

import dev.bengi.feedbackservice.domain.model.Project;

import java.util.List;
import java.util.UUID;

public interface ProjectUseCase {
    Project createProject(Project project);

    List<Project> getAllProjects();

    Project getProjectById(UUID id);

    Project updateProject(UUID id, Project project);

    void deleteProject(UUID id);
}