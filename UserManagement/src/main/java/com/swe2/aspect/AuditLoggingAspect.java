package com.swe2.aspect;

import com.swe2.annotation.Auditable;
import com.swe2.model.entity.AuditLog;
import com.swe2.service.AuditLogService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * AOP Aspect for automatic audit logging
 * Intercepts methods annotated with @Auditable and creates audit log entries
 */
@Aspect
@Component
public class AuditLoggingAspect {

    @Autowired
    private AuditLogService auditLogService;

    @Value("${spring.application.name:UNKNOWN}")
    private String serviceName;

    /**
     * Around advice for methods annotated with @Auditable
     * Logs the method execution with user context, timing, and result
     */
    @Around("@annotation(auditable)")
    public Object logAuditTrail(ProceedingJoinPoint joinPoint, Auditable auditable) throws Throwable {
        long startTime = System.currentTimeMillis();

        // Create audit log entry
        AuditLog auditLog = new AuditLog();
        auditLog.setAction(auditable.action());
        auditLog.setEntityType(auditable.entityType());
        auditLog.setDescription(auditable.description());
        auditLog.setServiceName(serviceName);

        // Extract HTTP request details
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();

                auditLog.setHttpMethod(request.getMethod());
                auditLog.setEndpoint(request.getRequestURI());
                auditLog.setIpAddress(getClientIP(request));

                // Extract user info from request headers (JWT)
                String authHeader = request.getHeader("Authorization");
                if (authHeader != null && authHeader.startsWith("Bearer ")) {
                    // Token is present - extract user info if needed
                    // For now, we'll rely on controller to set user details
                }

                // Log request parameters if enabled
                if (auditable.logParams()) {
                    String params = request.getParameterMap().toString();
                    if (params.length() > 2000) {
                        params = params.substring(0, 2000); // Truncate
                    }
                    auditLog.setRequestParams(params);
                }
            }
        } catch (Exception e) {
            // Continue if we can't extract request details
            System.err.println("Failed to extract request details for audit log: " + e.getMessage());
        }

        Object result = null;
        try {
            // Execute the actual method
            result = joinPoint.proceed();

            // Record success
            auditLog.setStatus("SUCCESS");
            auditLog.setResponseStatusCode(200);

            // Extract entity ID from result if it's a simple entity
            if (result != null && auditable.entityType() != null && !auditable.entityType().isEmpty()) {
                try {
                    // Try to get ID from result
                    Method getId = result.getClass().getMethod("getId");
                    Object id = getId.invoke(result);
                    if (id != null) {
                        auditLog.setEntityId(id.toString());
                    }
                } catch (Exception e) {
                    // Ignore if we can't extract ID
                }
            }

            return result;

        } catch (Exception e) {
            // Record failure
            auditLog.setStatus("FAILURE");
            auditLog.setErrorMessage(e.getMessage());
            auditLog.setResponseStatusCode(500);
            throw e;

        } finally {
            // Calculate duration
            long duration = System.currentTimeMillis() - startTime;
            auditLog.setDurationMs(duration);

            // Log asynchronously (non-blocking)
            auditLogService.logAsync(auditLog);
        }
    }

    /**
     * Extract client IP address from request
     * Handles X-Forwarded-For header for proxied requests
     */
    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null || xfHeader.isEmpty()) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0].trim();
    }
}
