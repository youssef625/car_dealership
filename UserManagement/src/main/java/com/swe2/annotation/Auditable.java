package com.swe2.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods that should be audited
 * The AOP aspect will intercept these methods and create audit log entries
 * 
 * Usage:
 * 
 * @Auditable(action = "CREATE_USER", entityType = "User", description = "Create
 *                   new user")
 *                   public User createUser(User user) { ... }
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
public @interface Auditable {

    /**
     * The action being performed (e.g., "LOGIN", "CREATE_CAR", "UPDATE_USER")
     */
    String action();

    /**
     * The type of entity being acted upon (e.g., "User", "Car", "Offer")
     * Optional - use for CRUD operations
     */
    String entityType() default "";

    /**
     * Human-readable description of the action
     * Optional - will be logged for context
     */
    String description() default "";

    /**
     * Whether to log request parameters
     * Default: false (to avoid logging sensitive data)
     */
    boolean logParams() default false;
}
