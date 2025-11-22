package com.swe2.model.dto;


import com.swe2.model.Enum.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class UserCreateRequest {

    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;


    private Role roleId;

    public UserCreateRequest() {}

    public UserCreateRequest(String name, String email, String password, Role roleId) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roleId = roleId;
    }


    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Role getRoleId() { return roleId; }
    public void setRoleId(Role roleId) { this.roleId = roleId; }
}