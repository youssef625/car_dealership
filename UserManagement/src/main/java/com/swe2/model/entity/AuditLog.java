package com.swe2.model.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_log", indexes = {
        @Index(name = "idx_user_id", columnList = "user_id"),
        @Index(name = "idx_action", columnList = "action"),
        @Index(name = "idx_timestamp", columnList = "timestamp"),
        @Index(name = "idx_service", columnList = "service_name"),
        @Index(name = "idx_entity", columnList = "entity_type, entity_id")
})
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_email", length = 500)
    private String userEmail;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "ip_address", length = 45) // IPv4 or IPv6
    private String ipAddress;

    // WHAT action was performed
    @Column(name = "action", nullable = false, length = 50)
    private String action; // LOGIN, CREATE_CAR, UPDATE_USER, etc.

    @Column(name = "entity_type", length = 50)
    private String entityType; // User, Car, Offer, etc.

    @Column(name = "entity_id", length = 50)
    private String entityId;

    @Column(name = "description", length = 1000)
    private String description;

    @Column(name = "http_method", length = 10)
    private String httpMethod; // GET, POST, PUT, DELETE

    @Column(name = "endpoint", length = 500)
    private String endpoint;

    // WHEN and WHERE
    @Column(name = "timestamp", nullable = false)
    private LocalDateTime timestamp;

    @Column(name = "service_name", length = 50)
    private String serviceName; // AUTHENTICATION, USERMANAGEMENT, CARSMANAGEMENT

    // RESULT
    @Column(name = "status", length = 20)
    private String status; // SUCCESS, FAILURE, UNAUTHORIZED

    @Column(name = "error_message", length = 1000)
    private String errorMessage;

    // Request/Response details (for debugging)
    @Column(name = "request_params", length = 2000)
    private String requestParams;

    @Column(name = "response_status_code")
    private Integer responseStatusCode;

    @Column(name = "duration_ms")
    private Long durationMs; // How long the operation took

    // Constructors
    public AuditLog() {
        this.timestamp = LocalDateTime.now();
    }

    public AuditLog(String action, String serviceName) {
        this();
        this.action = action;
        this.serviceName = serviceName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getRequestParams() {
        return requestParams;
    }

    public void setRequestParams(String requestParams) {
        this.requestParams = requestParams;
    }

    public Integer getResponseStatusCode() {
        return responseStatusCode;
    }

    public void setResponseStatusCode(Integer responseStatusCode) {
        this.responseStatusCode = responseStatusCode;
    }

    public Long getDurationMs() {
        return durationMs;
    }

    public void setDurationMs(Long durationMs) {
        this.durationMs = durationMs;
    }
}
