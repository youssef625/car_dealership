package com.swe2.Authentication.service;

import com.swe2.Authentication.Enum.Role;
import com.swe2.Authentication.model.*;
import com.swe2.Authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private GoogleOAuth2Service googleOAuth2Service;

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        // check if user is banned
        if (user.isBanned()) {
            return new LoginResponse(List.of("email: user is banned"));
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new LoginResponse(List.of("email: invalid email or password"));
        }
        if (user.getRole() == Role.employee && !user.isVerified()) {
            return new LoginResponse(List.of("email: user is not verified"));
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole());

        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole());
    }

    public LoginResponse loginWithGoogle(OAuth2LoginRequest request) {
        try {
            // Verify Google ID token and extract user info
            OAuth2UserInfo userInfo = googleOAuth2Service.verifyAndExtractUserInfo(request.getIdToken());

            // Try to find existing user by OAuth provider
            User user = userRepository.findByOauthProvider("google", userInfo.getSub());

            // If not found, try to find by email (in case user registered traditionally)
            if (user == null) {
                user = userRepository.findByEmail(userInfo.getEmail());
            }

            // If still not found, create new user
            if (user == null) {
                OAuth2RegisterRequest registerRequest = new OAuth2RegisterRequest(
                        userInfo.getEmail(),
                        userInfo.getName(),
                        "google",
                        userInfo.getSub(),
                        true, // Email verified by Google
                        Role.user // Default role for new users
                );
                user = userRepository.createOrUpdateOAuthUser(registerRequest);
            } else if (user.getOauthProvider() == null) {
                // User exists with traditional auth, link Google account
                OAuth2RegisterRequest updateRequest = new OAuth2RegisterRequest(
                        user.getEmail(),
                        user.getName(),
                        "google",
                        userInfo.getSub(),
                        true,
                        user.getRole());
                user = userRepository.createOrUpdateOAuthUser(updateRequest);
            }

            // Check if user is banned
            if (user.isBanned()) {
                return new LoginResponse(List.of("email: user is banned"));
            }

            // Generate JWT token
            String token = jwtService.generateToken(
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole());

            return new LoginResponse(
                    token,
                    user.getId(),
                    user.getEmail(),
                    user.getName(),
                    user.getRole());

        } catch (Exception e) {
            throw new RuntimeException("Google authentication failed: " + e.getMessage(), e);
        }
    }

    public RegisterResponse register(RegisterRequest request) {

        try {

            RegisterResponse response = userRepository.registerUser(request);
            if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                return response;
            }
            String token = jwtService.generateToken(
                    response.getId(),
                    response.getEmail(),
                    response.getName(),
                    response.getRole());
            response.token = token;

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public RegisterResponse registerAdmin(RegisterRequest request) {

        try {

            request.setRoleId(Role.employee);
            RegisterResponse response = userRepository.registerUser(request);
            if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                return response;
            }
            String token = jwtService.generateToken(
                    response.getId(),
                    response.getEmail(),
                    response.getName(),
                    response.getRole());
            response.token = token;

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public TokenValidationResponse validateToken(String token) {
        try {
            if (jwtService.validateToken(token)) {
                TokenValidationResponse response = new TokenValidationResponse(true, "Token is valid");
                response.setUserId(jwtService.extractUserId(token));
                response.setEmail(jwtService.extractEmail(token));
                response.setRole(jwtService.extractRole(token));
                return response;
            } else {
                return new TokenValidationResponse(false, "Token is invalid or expired");
            }
        } catch (Exception e) {
            return new TokenValidationResponse(false, "Token validation failed: " + e.getMessage());
        }
    }
}