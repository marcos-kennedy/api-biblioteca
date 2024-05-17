package com.projetos.biblioteca.models.user;

public enum UserRole {
    ADMIN("admin");
    
    private String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
