package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class AddProjectMemberRequest {
    private List<UUID> userIds;
    private String role;
} 