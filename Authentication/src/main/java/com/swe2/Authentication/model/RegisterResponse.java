package com.swe2.Authentication.model;

import com.swe2.Authentication.Enum.Role;

import java.util.List;

public class RegisterResponse {
   private List<String> errors;
   public String token;
    private int id;
    private String name;
    private String email;
    private Role role;

    public RegisterResponse() {
        this.errors = null;
    }

    public RegisterResponse(User _user){
         id = _user.getId();
         name = _user.getName();
         email = _user.getEmail();
         role = _user.getRole();
         this.errors = null;
    }

    public RegisterResponse(String token, Integer userId, String _email, String _name, Role _role) {
        this.token = token;
        id = userId;
        email = _email;
        name = _name;
        role = _role;
        this.errors = null;
    }

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;}
    public Role getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
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
