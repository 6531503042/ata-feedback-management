package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.port.input.ProjectUseCase;
import dev.bengi.feedbackservice.infrastructure.mapper.ProjectMapper;
import dev.bengi.feedbackservice.presentation.dto.request.CreateProjectRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import dev.bengi.feedbackservice.presentation.dto.response.ProjectResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectUseCase projectUseCase;
    private final ProjectMapper projectMapper;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectResponse>> createProject(
            @RequestBody @Valid CreateProjectRequest request) {
        var project = projectUseCase.createProject(projectMapper.toProject(request));
        return ResponseEntity.ok(ApiResponse.success(projectMapper.toProjectResponse(project)));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getAllProjects() {
        var projects = projectUseCase.getAllProjects()
                .stream()
                .map(project -> projectMapper.toProjectResponse(project))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ApiResponse.success(projects));
    }

    @PostMapping("/{projectId}/questions")
    public ResponseEntity<ApiResponse<ProjectResponse>> addQuestionsToProject(
            @PathVariable UUID projectId,
            @RequestBody @Valid List<UUID> questionIds) {
        var project = projectUseCase.updateProject(projectId, projectUseCase.getProjectById(projectId));
        return ResponseEntity.ok(ApiResponse.success(projectMapper.toProjectResponse(project)));
    }
}