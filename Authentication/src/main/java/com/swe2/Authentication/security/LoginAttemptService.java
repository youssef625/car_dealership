package com.swe2.Authentication.security;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 * Service to track and prevent brute force attacks on authentication endpoints
 * Blocks IPs after multiple failed login attempts
 */
@Service
public class LoginAttemptService {

    private static final int MAX_ATTEMPTS = 5;
    private static final long BLOCK_DURATION_MINUTES = 15;

    // Track failed attempts per IP address
    private final ConcurrentHashMap<String, AttemptInfo> attemptsCache = new ConcurrentHashMap<>();

    /**
     * Record a failed login attempt for an IP address
     * 
     * @param ipAddress The IP address to track
     */
    public void loginFailed(String ipAddress) {
        AttemptInfo info = attemptsCache.computeIfAbsent(ipAddress, k -> new AttemptInfo());
        info.incrementAttempts();
    }

    /**
     * Clear failed attempts for an IP address (on successful login)
     * 
     * @param ipAddress The IP address to clear
     */
    public void loginSucceeded(String ipAddress) {
        attemptsCache.remove(ipAddress);
    }

    /**
     * Check if an IP address is currently blocked
     * 
     * @param ipAddress The IP address to check
     * @return true if blocked, false otherwise
     */
    public boolean isBlocked(String ipAddress) {
        AttemptInfo info = attemptsCache.get(ipAddress);
        if (info == null) {
            return false;
        }

        // Check if block has expired
        if (info.isBlockExpired()) {
            attemptsCache.remove(ipAddress);
            return false;
        }

        return info.getAttempts() >= MAX_ATTEMPTS;
    }

    /**
     * Get remaining block time in minutes
     * 
     * @param ipAddress The IP address
     * @return minutes remaining, or 0 if not blocked
     */
    public long getRemainingBlockTime(String ipAddress) {
        AttemptInfo info = attemptsCache.get(ipAddress);
        if (info == null || info.isBlockExpired()) {
            return 0;
        }

        long elapsedMinutes = TimeUnit.MILLISECONDS.toMinutes(
                System.currentTimeMillis() - info.getFirstAttemptTime());
        return Math.max(0, BLOCK_DURATION_MINUTES - elapsedMinutes);
    }

    /**
     * Inner class to track attempt information
     */
    private static class AttemptInfo {
        private int attempts = 0;
        private long firstAttemptTime = System.currentTimeMillis();

        public void incrementAttempts() {
            attempts++;
            // Reset timer if this is the first attempt in a new window
            if (attempts == 1) {
                firstAttemptTime = System.currentTimeMillis();
            }
        }

        public int getAttempts() {
            return attempts;
        }

        public long getFirstAttemptTime() {
            return firstAttemptTime;
        }

        public boolean isBlockExpired() {
            long elapsedTime = System.currentTimeMillis() - firstAttemptTime;
            return elapsedTime > TimeUnit.MINUTES.toMillis(BLOCK_DURATION_MINUTES);
        }
    }
}
