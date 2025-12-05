package com.swe2.Authentication.model;

public class OAuth2UserInfo {
    private String email;
    private String name;
    private String sub; // Google user ID
    private boolean emailVerified;

    public OAuth2UserInfo() {
    }

    public OAuth2UserInfo(String email, String name, String sub, boolean emailVerified) {
        this.email = email;
        this.name = name;
        this.sub = sub;
        this.emailVerified = emailVerified;
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

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
