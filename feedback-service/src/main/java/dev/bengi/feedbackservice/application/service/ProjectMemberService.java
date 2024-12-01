package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.domain.model.ProjectMember;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.ProjectMemberRepository;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.ProjectRepository;
import dev.bengi.feedbackservice.presentation.dto.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {
    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;

    @Transactional
    public ApiResponse<Void> addMembers(UUID projectId, List<UUID> userIds, String role) {
        projectRepository.findById(projectId)
            .orElseThrow(() -> new RuntimeException("Project not found"));

        userIds.forEach(userId -> {
            if (!projectMemberRepository.existsByProjectIdAndUserId(projectId, userId)) {
                ProjectMember member = ProjectMember.builder()
                    .id(UUID.randomUUID())
                    .projectId(projectId)
                    .userId(userId)
                    .role(role)
                    .active(true)
                    .build();
                projectMemberRepository.save(member);
            }
        });
        
        return ApiResponse.success(null);
    }

    @Transactional
    public ApiResponse<Void> removeMember(UUID projectId, UUID userId) {
        ProjectMember member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
            .orElseThrow(() -> new RuntimeException("Project member not found"));
        projectMemberRepository.delete(member);
        return ApiResponse.success(null);
    }

    @Transactional(readOnly = true)
    public List<ProjectMember> getProjectMembers(UUID projectId) {
        return projectMemberRepository.findByProjectId(projectId);
    }

    @Transactional
    public ApiResponse<Void> updateMemberRole(UUID projectId, UUID userId, String role) {
        ProjectMember member = projectMemberRepository.findByProjectIdAndUserId(projectId, userId)
            .orElseThrow(() -> new RuntimeException("Project member not found"));
        member.setRole(role);
        projectMemberRepository.save(member);
        return ApiResponse.success(null);
    }

    public List<UUID> findProjectIdsByUserId(UUID userId) {
        return projectMemberRepository.findByUserId(userId).stream()
                .map(ProjectMember::getProjectId)
                .toList();
    }
} 