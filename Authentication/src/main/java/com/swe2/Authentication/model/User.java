package com.swe2.Authentication.model;

import com.swe2.Authentication.Enum.Role;

public class User {
    protected Integer id;
    protected String name;
    protected String email;
    protected String password; // hashed password
    protected Role role;
    protected boolean approved;

    // Add banned flag so Feign deserializes it from UserManagement responses
    protected boolean banned = false;

    public User() {
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isBanned() {
        return banned;
    }

    public boolean isVerified() {
        return approved;
    }
}
