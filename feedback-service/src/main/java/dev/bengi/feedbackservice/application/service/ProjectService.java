package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.application.port.input.ProjectUseCase;
import dev.bengi.feedbackservice.domain.model.Project;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService implements ProjectUseCase {
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public Project createProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Project getProjectById(UUID id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    @Transactional
    public Project updateProject(UUID id, Project project) {
        Project existingProject = getProjectById(id);
        existingProject.setName(project.getName());
        existingProject.setDescription(project.getDescription());
        existingProject.setFeedbackStartDate(project.getFeedbackStartDate());
        existingProject.setFeedbackEndDate(project.getFeedbackEndDate());
        existingProject.setActive(project.isActive());
        return projectRepository.save(existingProject);
    }

    @Override
    @Transactional
    public void deleteProject(UUID id) {
        projectRepository.deleteById(id);
    }
}