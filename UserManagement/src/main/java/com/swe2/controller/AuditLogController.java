package com.swe2.controller;

import com.swe2.model.entity.AuditLog;
import com.swe2.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Controller for viewing and querying audit logs
 * Requires admin role for most endpoints
 */
@RestController
@RequestMapping("/api/audit")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    /**
     * Get all audit logs with filters
     * 
     * @param userId      Filter by user ID (optional)
     * @param action      Filter by action type (optional)
     * @param serviceName Filter by service name (optional)
     * @param startDate   Filter by start date (optional, format:
     *                    yyyy-MM-dd'T'HH:mm:ss)
     * @param endDate     Filter by end date (optional)
     * @param page        Page number (default: 0)
     * @param size        Page size (default: 50, max: 100)
     * @return Page of audit logs
     */
    @GetMapping
    public ResponseEntity<Page<AuditLog>> getAuditLogs(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String serviceName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        // Limit page size to prevent DoS
        if (size > 100) {
            size = 100;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());

        Page<AuditLog> logs = auditLogService.searchLogs(
                userId, action, serviceName, startDate, endDate, pageable);

        return ResponseEntity.ok(logs);
    }

    /**
     * Get audit logs for a specific user
     * 
     * @param userId User ID
     * @param page   Page number
     * @param size   Page size
     * @return Page of audit logs for the user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<AuditLog>> getUserActivityPaginated(
            @PathVariable Integer userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        if (size > 100) {
            size = 100;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<AuditLog> logs = auditLogService.getUserLogs(userId, pageable);

        return ResponseEntity.ok(logs);
    }

    /**
     * Get recent activity for a user (last 24 hours)
     * 
     * @param userId User ID
     * @return List of recent audit logs
     */
    @GetMapping("/user/{userId}/recent")
    public ResponseEntity<List<AuditLog>> getRecentUserActivity(@PathVariable Integer userId) {
        LocalDateTime startTime = LocalDateTime.now().minusHours(24);
        LocalDateTime endTime = LocalDateTime.now();

        List<AuditLog> logs = auditLogService.getUserActivity(userId, startTime, endTime);

        return ResponseEntity.ok(logs);
    }

    /**
     * Get audit logs for a specific entity
     * 
     * @param entityType Type of entity (e.g., "Car", "User")
     * @param entityId   ID of the entity
     * @param page       Page number
     * @param size       Page size
     * @return Page of audit logs for the entity
     */
    @GetMapping("/entity/{entityType}/{entityId}")
    public ResponseEntity<Page<AuditLog>> getEntityLogs(
            @PathVariable String entityType,
            @PathVariable String entityId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {

        if (size > 100) {
            size = 100;
        }

        Pageable pageable = PageRequest.of(page, size, Sort.by("timestamp").descending());
        Page<AuditLog> logs = auditLogService.getEntityLogs(entityType, entityId, pageable);

        return ResponseEntity.ok(logs);
    }

    /**
     * Get recent failed actions (for security monitoring)
     * 
     * @param hours Number of hours to look back (default: 24)
     * @return List of failed actions
     */
    @GetMapping("/failures")
    public ResponseEntity<List<AuditLog>> getRecentFailures(
            @RequestParam(defaultValue = "24") int hours) {

        // Limit to prevent excessive queries
        if (hours > 168) { // Max 1 week
            hours = 168;
        }

        List<AuditLog> failures = auditLogService.getRecentFailures(hours);

        return ResponseEntity.ok(failures);
    }

    /**
     * Get count of user actions in a time period
     * 
     * @param userId    User ID
     * @param startDate Start date
     * @param endDate   End date
     * @return Count of actions
     */
    @GetMapping("/user/{userId}/count")
    public ResponseEntity<Long> countUserActions(
            @PathVariable Integer userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        long count = auditLogService.countUserActions(userId, startDate, endDate);

        return ResponseEntity.ok(count);
    }
}
