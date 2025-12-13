package com.swe2.Authentication.model;

import com.swe2.Authentication.Enum.Role;

import java.util.List;

public class LoginResponse {
    private String token;
    private String type = "Bearer";
    private Integer userId;
    private String email;
    private String name;
    private Role role;
    private List<String> errors;

    public LoginResponse() {}

    public LoginResponse(String token, Integer userId, String email, String name, Role role) {

        this.token = token;
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.role = role;
    }
    public LoginResponse(List<String> errors) {
        this.errors = errors;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Integer getUserId() { return userId; }
    public void setUserId(Integer userId) { this.userId = userId; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public List<String> getErrors() {
        return errors;
    }

    public boolean hasErrors() {
        return errors != null && !errors.isEmpty();
    }
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}