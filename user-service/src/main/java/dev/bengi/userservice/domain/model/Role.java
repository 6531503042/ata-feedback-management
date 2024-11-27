package dev.bengi.userservice.domain.model;

public enum Role {
    USER,
    EMPLOYEE,
    ADMIN;

    public String getValue() {
        return this.name();
    }
}