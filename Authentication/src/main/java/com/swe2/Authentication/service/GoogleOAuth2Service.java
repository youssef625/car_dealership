package com.swe2.Authentication.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.swe2.Authentication.model.OAuth2UserInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleOAuth2Service {

    @Value("${google.oauth.client-id}")
    private String clientId;

    private final GoogleIdTokenVerifier verifier;

    public GoogleOAuth2Service(@Value("${google.oauth.client-id}") String clientId) {
        this.clientId = clientId;
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                new GsonFactory())
                .setAudience(Collections.singletonList(clientId))
                .build();
    }

    /**
     * Verify Google ID token and extract user information
     * 
     * @param idTokenString The Google ID token from the frontend
     * @return OAuth2UserInfo containing user details
     * @throws GeneralSecurityException if token verification fails
     * @throws IOException              if network error occurs
     */
    public OAuth2UserInfo verifyAndExtractUserInfo(String idTokenString)
            throws GeneralSecurityException, IOException {

        if (idTokenString == null || idTokenString.isEmpty()) {
            throw new IllegalArgumentException("ID token cannot be null or empty");
        }

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken == null) {
            throw new SecurityException("Invalid ID token");
        }

        Payload payload = idToken.getPayload();

        String email = payload.getEmail();
        boolean emailVerified = payload.getEmailVerified();
        String name = (String) payload.get("name");
        String sub = payload.getSubject(); // Google user ID

        if (email == null || !emailVerified) {
            throw new SecurityException("Email not verified by Google");
        }

        return new OAuth2UserInfo(email, name, sub, emailVerified);
    }
}
