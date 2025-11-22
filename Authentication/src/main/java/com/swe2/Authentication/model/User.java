package com.swe2.Authentication.model;

import com.swe2.Authentication.Enum.Role;

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password; // hashed password
    private Role role;

    public User() {}

    // Getters and Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRole() { return role; }
    public void setRoleName(Role role) { this.role = role; }
}

