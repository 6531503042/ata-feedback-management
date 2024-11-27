package dev.bengi.feedbackservice.application.service;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackSearch;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.FeedbackRepository;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.ProjectMemberRepository;
import dev.bengi.feedbackservice.security.CurrentUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FeedbackSearchService {
    private final FeedbackRepository feedbackRepository;
    private final ProjectMemberRepository projectMemberRepository;

    public Page<Feedback> searchFeedbacks(FeedbackSearch search, Pageable pageable) {
        return feedbackRepository.searchFeedback(search, pageable);
    }

    public Page<Feedback> searchUserFeedbacks(CurrentUserDetails currentUser, FeedbackSearch search, Pageable pageable) {
        List<UUID> userProjectIds = projectMemberRepository.findProjectIdsByUserId(currentUser.getId());
        
        if (search.getProjectId() != null && !userProjectIds.contains(search.getProjectId())) {
            throw new IllegalArgumentException("User does not have access to this project");
        }
        
        // If no specific project is requested, limit search to user's projects
        if (search.getProjectId() == null) {
            search = FeedbackSearch.builder()
                    .projectId(search.getProjectId())
                    .userId(search.getUserId())
                    .userEmail(search.getUserEmail())
                    .category(search.getCategory())
                    .categories(search.getCategories())
                    .searchTerm(search.getSearchTerm())
                    .includePrivate(search.isIncludePrivate())
                    .build();
        }
        
        return feedbackRepository.searchFeedback(search, pageable);
    }
} 