package dev.bengi.feedbackservice.application.port.output;

import dev.bengi.feedbackservice.domain.model.Project;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectPort {
    Project save(Project project);
    Optional<Project> findById(UUID id);
    List<Project> findAll();
    void deleteById(UUID id);
    boolean existsByNameIgnoreCase(String name);
} 