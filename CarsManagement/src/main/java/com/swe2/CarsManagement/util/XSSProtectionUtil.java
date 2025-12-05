package com.swe2.CarsManagement.util;

import org.springframework.stereotype.Component;
import org.springframework.web.util.HtmlUtils;

import java.util.regex.Pattern;

/**
 * Utility class for XSS (Cross-Site Scripting) prevention
 * Sanitizes user input to prevent script injection attacks
 */
@Component
public class XSSProtectionUtil {

    // Pattern to detect potentially malicious scripts
    private static final Pattern[] XSS_PATTERNS = {
            Pattern.compile("<script>(.*?)</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("src[\r\n]*  =[\r\n]*['\"]?(.*?)['\"]?", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
            Pattern.compile("</script>", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
            Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
            Pattern.compile("expression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
            Pattern.compile("javascript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("vbscript:", Pattern.CASE_INSENSITIVE),
            Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
            Pattern.compile("onerror(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE),
            Pattern.compile("<iframe", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<object", Pattern.CASE_INSENSITIVE),
            Pattern.compile("<embed", Pattern.CASE_INSENSITIVE)
    };

    /**
     * Sanitize input string to prevent XSS attacks
     * Uses HTML entity encoding and pattern matching
     * 
     * @param input The user input to sanitize
     * @return Sanitized string safe for storage and display
     */
    public String sanitize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        // HTML encode special characters
        String sanitized = HtmlUtils.htmlEscape(input);

        // Additional check for known XSS patterns
        for (Pattern pattern : XSS_PATTERNS) {
            if (pattern.matcher(sanitized).find()) {
                // If malicious pattern detected, strip it completely
                sanitized = pattern.matcher(sanitized).replaceAll("");
            }
        }

        return sanitized.trim();
    }

    /**
     * Validate that input contains only safe characters
     * Allows alphanumeric, spaces, and basic punctuation
     * 
     * @param input The input to validate
     * @return true if input is safe, false otherwise
     */
    public boolean isSafeInput(String input) {
        if (input == null || input.isEmpty()) {
            return true;
        }

        // Allow letters, numbers, spaces, and basic punctuation
        // Disallow: <, >, ", ', ;, script tags, etc.
        Pattern safePattern = Pattern.compile("^[a-zA-Z0-9\\s.,\\-_()]+$");
        return safePattern.matcher(input).matches();
    }

    /**
     * Sanitize search query specifically for database searches
     * More permissive than general sanitization but still safe
     * 
     * @param query The search query
     * @return Sanitized query
     */
    public String sanitizeSearchQuery(String query) {
        if (query == null || query.isEmpty()) {
            return "";
        }

        // Remove any potential SQL injection or XSS attempts
        String sanitized = query.trim();

        // HTML encode
        sanitized = HtmlUtils.htmlEscape(sanitized);

        // Remove script tags and dangerous patterns
        for (Pattern pattern : XSS_PATTERNS) {
            sanitized = pattern.matcher(sanitized).replaceAll("");
        }

        // Limit length to prevent DoS
        if (sanitized.length() > 100) {
            sanitized = sanitized.substring(0, 100);
        }

        return sanitized;
    }
}
