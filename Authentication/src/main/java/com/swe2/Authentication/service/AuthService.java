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
            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );

        return new LoginResponse(
                token,
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getRole()
        );
    }

    public RegisterResponse register(RegisterRequest request){

        try {


            RegisterResponse response = userRepository.registerUser(request);
            if (response.getErrors() != null && !response.getErrors().isEmpty()) {
                return response;
            }
            String token = jwtService.generateToken(
                    response.getId(),
                    response.getEmail(),
                    response.getName(),
                    response.getRole()
            );
            response.token = token;

            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public RegisterResponse registerAdmin(RegisterRequest request){

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
                    response.getRole()
            );
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