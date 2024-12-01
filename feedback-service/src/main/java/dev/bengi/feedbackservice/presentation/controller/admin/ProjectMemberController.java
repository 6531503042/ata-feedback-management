package dev.bengi.feedbackservice.presentation.controller.admin;

import dev.bengi.feedbackservice.application.service.ProjectMemberService;
import dev.bengi.feedbackservice.domain.model.ProjectMember;
import dev.bengi.feedbackservice.presentation.dto.request.AddProjectMemberRequest;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {
    private final ProjectMemberService projectMemberService;

    @PostMapping
    public ResponseEntity<ApiResponse<Void>> addMembers(
            @PathVariable UUID projectId,
            @RequestBody AddProjectMemberRequest request
    ) {
        return ResponseEntity.ok(
            projectMemberService.addMembers(projectId, request.getUserIds(), request.getRole())
        );
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> removeMember(
            @PathVariable UUID projectId,
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(
            projectMemberService.removeMember(projectId, userId)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectMember>>> getMembers(
            @PathVariable UUID projectId
    ) {
        return ResponseEntity.ok(
            ApiResponse.success(projectMemberService.getProjectMembers(projectId))
        );
    }

    @PutMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<Void>> updateMemberRole(
            @PathVariable UUID projectId,
            @PathVariable UUID userId,
            @RequestParam String role
    ) {
        return ResponseEntity.ok(
            projectMemberService.updateMemberRole(projectId, userId, role)
        );
    }
} 