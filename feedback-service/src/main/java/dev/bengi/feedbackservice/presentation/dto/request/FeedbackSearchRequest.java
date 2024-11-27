package dev.bengi.feedbackservice.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackSearchRequest {
    private UUID projectId;
    private UUID userId;
    private String userEmail;
    private String userName;
    private List<String> userRoles;
    private List<String> privacyLevels;
    private List<String> categories;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
} 