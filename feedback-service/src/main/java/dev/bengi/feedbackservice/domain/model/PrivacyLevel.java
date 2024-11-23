package dev.bengi.feedbackservice.domain.model;

public enum PrivacyLevel {
    ANONYMOUS, // No one can see who gave the feedback
    VISIBLE_TO_HR, // Only HR can see who gave the feedback
    VISIBLE_TO_ALL // Everyone can see who gave the feedback
}