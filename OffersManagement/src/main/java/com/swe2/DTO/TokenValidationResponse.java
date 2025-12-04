package com.swe2.DTO;


public class TokenValidationResponse {
    private boolean valid;
    private String message;
    private Integer userId;
    private String email;
    private String role;

    public boolean isValid() {
        return valid;
    }

    public int getUserId() {
        return userId;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
