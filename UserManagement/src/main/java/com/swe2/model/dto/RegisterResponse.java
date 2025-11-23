package com.swe2.model.dto;

import com.swe2.model.Enum.Role;
import com.swe2.model.entity.User;
import jakarta.persistence.Column;

import java.time.LocalDateTime;
import java.util.List;

public class RegisterResponse {
    private Integer id;

    private String name;

    private String email;

    private String password;

    private Role role;

    private List<String> errors;

    public RegisterResponse() {
        this.errors = null;
    }

    public RegisterResponse (List<String> _errors){
        this.errors = _errors;
    }

    public RegisterResponse(User _user){
       id = _user.getId();
       name = _user.getName();
       email = _user.getEmail();
       password = _user.getPassword();
       role = _user.getRole();
       this.errors = null;
   }

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

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
