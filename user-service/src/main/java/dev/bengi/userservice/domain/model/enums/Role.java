package dev.bengi.userservice.domain.model.enums;

public enum Role {
    USER,
    EMPLOYEE,
    ADMIN;

    public String getValue() {
        return this.name();
    }

    public static Role fromString(String role) {
        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Role.USER;
        }
    }
} 