package com.swe2.Authentication.model;

import com.swe2.Authentication.Enum.Role;

public class OAuth2RegisterRequest {
    private String email;
    private String name;
    private String oauthProvider;
    private String oauthProviderId;
    private boolean emailVerified;
    private Role role;

    public OAuth2RegisterRequest() {
    }

    public OAuth2RegisterRequest(String email, String name, String oauthProvider,
            String oauthProviderId, boolean emailVerified, Role role) {
        this.email = email;
        this.name = name;
        this.oauthProvider = oauthProvider;
        this.oauthProviderId = oauthProviderId;
        this.emailVerified = emailVerified;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOauthProvider() {
        return oauthProvider;
    }

    public void setOauthProvider(String oauthProvider) {
        this.oauthProvider = oauthProvider;
    }

    public String getOauthProviderId() {
        return oauthProviderId;
    }

    public void setOauthProviderId(String oauthProviderId) {
        this.oauthProviderId = oauthProviderId;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
