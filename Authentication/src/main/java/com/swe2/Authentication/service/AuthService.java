package com.swe2.Authentication.service;

import com.swe2.Authentication.model.LoginRequest;
import com.swe2.Authentication.model.LoginResponse;
import com.swe2.Authentication.model.TokenValidationResponse;
import com.swe2.Authentication.model.User;
import com.swe2.Authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public LoginResponse login(LoginRequest request) {
        // Fetch user from UserManagement service via repository
        User user = userRepository.findByEmail(request.getEmail());

        if (user == null) {
            throw new RuntimeException("Invalid email or password");
        }

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // Generate JWT token
        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRoleName()
        );

        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRoleName()
        );
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