package com.swe2.CarsManagement.aspect;

import com.swe2.CarsManagement.dto.TokenValidationResponse;
import com.swe2.CarsManagement.client.TokenValidationClient;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class AuthorizationAspect {

    @Autowired
    private TokenValidationClient tokenValidationClient;

    @Before("@annotation(requiresRole)")
    public void checkAuthorization(JoinPoint joinPoint, RequiresRole requiresRole) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            throw new RuntimeException("No request context available");
        }

        HttpServletRequest request = attributes.getRequest();
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Missing or invalid Authorization header");
        }

        // Pass the full header to the validation client
        TokenValidationResponse response = tokenValidationClient.validateToken(authHeader);

        if (!response.isValid()) {
            throw new RuntimeException("Invalid token");
        }

        String userRole = response.getRole();
        List<String> allowedRoles = Arrays.asList(requiresRole.value());

        // Check if user role matches any of the allowed roles
        boolean isAuthorized = allowedRoles.stream()
                .anyMatch(role -> role.equalsIgnoreCase(userRole));

        if (!isAuthorized) {
            throw new RuntimeException("User does not have permission to access this resource");
        }
    }
}
