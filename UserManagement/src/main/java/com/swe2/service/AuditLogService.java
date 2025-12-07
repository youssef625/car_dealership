package com.swe2.service;

import com.swe2.model.entity.AuditLog;
import com.swe2.repository.AuditLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service for creating and querying audit logs
 * Uses async processing to avoid blocking main request threads
 */
@Service
public class AuditLogService {

    @Autowired
    private AuditLogRepository auditLogRepository;

    /**
     * Create an audit log entry asynchronously
     * This won't block the main request thread
     */
    @Async
    @Transactional
    public void logAsync(AuditLog auditLog) {
        try {
            auditLogRepository.save(auditLog);
        } catch (Exception e) {
            // Log error but don't throw - audit logging shouldn't break the application
            System.err.println("Failed to save audit log: " + e.getMessage());
        }
    }

    /**
     * Create an audit log entry synchronously
     * Use sparingly - prefer logAsync
     */
    @Transactional
    public AuditLog log(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }

    /**
     * Get all audit logs with pagination
     */
    public Page<AuditLog> getAllLogs(Pageable pageable) {
        return auditLogRepository.findAll(pageable);
    }

    /**
     * Get audit logs for a specific user
     */
    public Page<AuditLog> getUserLogs(Integer userId, Pageable pageable) {
        return auditLogRepository.findByUserId(userId, pageable);
    }

    /**
     * Get audit logs by action type
     */
    public Page<AuditLog> getLogsByAction(String action, Pageable pageable) {
        return auditLogRepository.findByAction(action, pageable);
    }

    /**
     * Get audit logs for a specific entity
     */
    public Page<AuditLog> getEntityLogs(String entityType, String entityId, Pageable pageable) {
        return auditLogRepository.findByEntityTypeAndEntityId(entityType, entityId, pageable);
    }

    /**
     * Get user activity within a time range
     */
    public List<AuditLog> getUserActivity(Integer userId, LocalDateTime startTime, LocalDateTime endTime) {
        return auditLogRepository.findUserActivity(userId, startTime, endTime);
    }

    /**
     * Search audit logs with multiple filters
     */
    public Page<AuditLog> searchLogs(Integer userId, String action, String serviceName,
            LocalDateTime startTime, LocalDateTime endTime,
            Pageable pageable) {
        return auditLogRepository.searchAuditLogs(userId, action, serviceName, startTime, endTime, pageable);
    }

    /**
     * Get recent failed actions (for security monitoring)
     */
    public List<AuditLog> getRecentFailures(int hours) {
        LocalDateTime since = LocalDateTime.now().minusHours(hours);
        return auditLogRepository.findRecentFailures(since);
    }

    /**
     * Count user actions in a time period
     */
    public long countUserActions(Integer userId, LocalDateTime startTime, LocalDateTime endTime) {
        return auditLogRepository.countUserActions(userId, startTime, endTime);
    }
}
