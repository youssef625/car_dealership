package com.swe2.Authentication.model;

public class OAuth2LoginRequest {
    private String idToken;

    public OAuth2LoginRequest() {
    }

    public OAuth2LoginRequest(String idToken) {
        this.idToken = idToken;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }
}
