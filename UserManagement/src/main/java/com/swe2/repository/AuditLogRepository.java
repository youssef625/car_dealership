package com.swe2.repository;

import com.swe2.model.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository for querying audit logs
 * Provides methods for filtering and searching audit trail
 */
@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {

    /**
     * Find all audit logs for a specific user
     */
    Page<AuditLog> findByUserId(Integer userId, Pageable pageable);

    /**
     * Find audit logs by action type
     */
    Page<AuditLog> findByAction(String action, Pageable pageable);

    /**
     * Find audit logs by service name
     */
    Page<AuditLog> findByServiceName(String serviceName, Pageable pageable);

    /**
     * Find audit logs by entity type and ID
     */
    Page<AuditLog> findByEntityTypeAndEntityId(String entityType, String entityId, Pageable pageable);

    /**
     * Find audit logs within a time range
     */
    Page<AuditLog> findByTimestampBetween(LocalDateTime startTime, LocalDateTime endTime, Pageable pageable);

    /**
     * Find audit logs for a user within a time range
     */
    @Query("SELECT a FROM AuditLog a WHERE a.userId = :userId " +
            "AND a.timestamp BETWEEN :startTime AND :endTime " +
            "ORDER BY a.timestamp DESC")
    List<AuditLog> findUserActivity(@Param("userId") Integer userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);

    /**
     * Find failed actions (for security monitoring)
     */
    @Query("SELECT a FROM AuditLog a WHERE a.status = 'FAILURE' " +
            "AND a.timestamp >= :since ORDER BY a.timestamp DESC")
    List<AuditLog> findRecentFailures(@Param("since") LocalDateTime since);

    /**
     * Complex search with multiple filters
     */
    @Query("SELECT a FROM AuditLog a WHERE " +
            "(:userId IS NULL OR a.userId = :userId) AND " +
            "(:action IS NULL OR a.action = :action) AND " +
            "(:serviceName IS NULL OR a.serviceName = :serviceName) AND " +
            "(:startTime IS NULL OR a.timestamp >= :startTime) AND " +
            "(:endTime IS NULL OR a.timestamp <= :endTime)")
    Page<AuditLog> searchAuditLogs(@Param("userId") Integer userId,
            @Param("action") String action,
            @Param("serviceName") String serviceName,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime,
            Pageable pageable);

    /**
     * Count actions by user in a time period (for analytics)
     */
    @Query("SELECT COUNT(a) FROM AuditLog a WHERE a.userId = :userId " +
            "AND a.timestamp BETWEEN :startTime AND :endTime")
    long countUserActions(@Param("userId") Integer userId,
            @Param("startTime") LocalDateTime startTime,
            @Param("endTime") LocalDateTime endTime);
}
