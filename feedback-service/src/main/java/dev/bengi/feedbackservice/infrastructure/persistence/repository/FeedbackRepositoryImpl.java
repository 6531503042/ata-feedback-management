package dev.bengi.feedbackservice.infrastructure.persistence.repository;

import dev.bengi.feedbackservice.domain.model.Feedback;
import dev.bengi.feedbackservice.domain.model.FeedbackSearch;
import dev.bengi.feedbackservice.infrastructure.persistence.entity.FeedbackEntity;
import dev.bengi.feedbackservice.infrastructure.persistence.mapper.FeedbackMapper;
import dev.bengi.feedbackservice.infrastructure.persistence.repository.FeedbackRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class FeedbackRepositoryImpl implements FeedbackRepositoryCustom {
    private final EntityManager em;
    private final FeedbackMapper feedbackMapper;

    @Override
    public Page<Feedback> searchFeedback(FeedbackSearch search, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Feedback> query = cb.createQuery(Feedback.class);
        Root<Feedback> feedback = query.from(Feedback.class);
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (search.getProjectId() != null) {
            predicates.add(cb.equal(feedback.get("projectId"), search.getProjectId()));
        }
        
        if (!search.isIncludePrivate()) {
            predicates.add(cb.notEqual(feedback.get("privacyLevel"), "PRIVATE"));
        }
        
        if (search.getCategories() != null && !search.getCategories().isEmpty()) {
            predicates.add(feedback.get("category").in(search.getCategories()));
        }
        
        if (search.getSearchTerm() != null && !search.getSearchTerm().isEmpty()) {
            predicates.add(cb.like(
                cb.lower(feedback.get("description")),
                "%" + search.getSearchTerm().toLowerCase() + "%"
            ));
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
        TypedQuery<Feedback> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());
        
        List<Feedback> results = typedQuery.getResultList();
        
        return new PageImpl<>(results, pageable, count(search));
    }
    
    private long count(FeedbackSearch search) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<Feedback> feedback = query.from(Feedback.class);
        
        query.select(cb.count(feedback));
        
        List<Predicate> predicates = new ArrayList<>();
        
        if (search.getProjectId() != null) {
            predicates.add(cb.equal(feedback.get("projectId"), search.getProjectId()));
        }
        
        if (!search.isIncludePrivate()) {
            predicates.add(cb.notEqual(feedback.get("privacyLevel"), "PRIVATE"));
        }
        
        if (search.getCategories() != null && !search.getCategories().isEmpty()) {
            predicates.add(feedback.get("category").in(search.getCategories()));
        }
        
        query.where(predicates.toArray(new Predicate[0]));
        
        return em.createQuery(query).getSingleResult();
    }
} 