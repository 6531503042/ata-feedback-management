package dev.bengi.feedbackservice.domain.model.enums;

public enum PrivacyLevel {
    PUBLIC("PUBLIC"),
    PRIVATE("PRIVATE"),
    ANONYMOUS("ANONYMOUS");

    private final String value;

    PrivacyLevel(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PrivacyLevel fromValue(String value) {
        for (PrivacyLevel level : values()) {
            if (level.value.equalsIgnoreCase(value)) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown privacy level: " + value);
    }
}