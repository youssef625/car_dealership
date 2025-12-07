package com.swe2.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256-GCM encryption utility for encrypting sensitive data
 * Uses authenticated encryption with random IV for each encryption
 * Lazy initialization allows application to start without encryption key set
 */
@Component
public class AESEncryptionUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits
    private static final int GCM_IV_LENGTH = 12; // bytes
    private static final int AES_KEY_SIZE = 256; // bits

    @Value("${app.encryption.key:}")
    private String encryptionKeyBase64;

    private SecretKey secretKey;

    /**
     * Get or initialize the secret key
     * Lazy initialization to allow application to start even if key is not
     * configured
     */
    private SecretKey getSecretKey() {
        if (secretKey == null) {
            if (encryptionKeyBase64 == null || encryptionKeyBase64.trim().isEmpty()) {
                throw new IllegalStateException(
                        "Encryption key not configured. Please set ENCRYPTION_KEY environment variable. " +
                                "Generate with: openssl rand -base64 32");
            }

            try {
                byte[] decodedKey = Base64.getDecoder().decode(encryptionKeyBase64);
                if (decodedKey.length != 32) { // 256 bits = 32 bytes
                    throw new IllegalArgumentException(
                            "Invalid encryption key length: " + decodedKey.length + " bytes. " +
                                    "Expected 32 bytes (256 bits). Generate with: openssl rand -base64 32");
                }
                secretKey = new SecretKeySpec(decodedKey, "AES");
            } catch (IllegalArgumentException e) {
                throw new IllegalStateException("Invalid encryption key format: " + e.getMessage(), e);
            }
        }
        return secretKey;
    }

    /**
     * Encrypt a string using AES-256-GCM
     * Returns Base64 encoded: IV + Ciphertext
     */
    public String encrypt(String plaintext) {
        if (plaintext == null || plaintext.isEmpty()) {
            return plaintext;
        }

        try {
            // Generate random IV
            byte[] iv = new byte[GCM_IV_LENGTH];
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKey(), parameterSpec);

            // Encrypt
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

            // Combine IV + Ciphertext
            byte[] combined = new byte[iv.length + ciphertext.length];
            System.arraycopy(iv, 0, combined, 0, iv.length);
            System.arraycopy(ciphertext, 0, combined, iv.length, ciphertext.length);

            // Return Base64 encoded
            return Base64.getEncoder().encodeToString(combined);

        } catch (Exception e) {
            throw new RuntimeException("Encryption failed", e);
        }
    }

    /**
     * Decrypt a Base64 encoded string (IV + Ciphertext)
     * Returns the original plaintext
     */
    public String decrypt(String encryptedBase64) {
        if (encryptedBase64 == null || encryptedBase64.isEmpty()) {
            return encryptedBase64;
        }

        try {
            // Decode Base64
            byte[] combined = Base64.getDecoder().decode(encryptedBase64);

            // Extract IV and ciphertext
            byte[] iv = new byte[GCM_IV_LENGTH];
            byte[] ciphertext = new byte[combined.length - GCM_IV_LENGTH];
            System.arraycopy(combined, 0, iv, 0, iv.length);
            System.arraycopy(combined, iv.length, ciphertext, 0, ciphertext.length);

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), parameterSpec);

            // Decrypt
            byte[] plaintext = cipher.doFinal(ciphertext);

            return new String(plaintext, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("Decryption failed", e);
        }
    }

    /**
     * Generate a new random AES-256 key (for initial setup)
     * 
     * @return Base64-encoded 256-bit key
     */
    public static String generateKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(AES_KEY_SIZE);
            SecretKey key = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Key generation failed: " + e.getMessage(), e);
        }
    }
}
