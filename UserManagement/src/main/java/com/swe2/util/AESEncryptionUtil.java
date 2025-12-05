package com.swe2.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256-GCM encryption utility for encrypting sensitive data
 * Uses authenticated encryption with random IV for each encryption
 */
@Component
public class AESEncryptionUtil {

    private static final String ALGORITHM = "AES/GCM/NoPadding";
    private static final int GCM_TAG_LENGTH = 128; // bits
    private static final int GCM_IV_LENGTH = 12; // bytes
    private static final int AES_KEY_SIZE = 256; // bits

    private final SecretKey secretKey;

    public AESEncryptionUtil(@Value("${app.encryption.key:}") String base64Key) {
        if (base64Key == null || base64Key.isEmpty()) {
            throw new IllegalStateException(
                    "Encryption key not configured. Set app.encryption.key or ENCRYPTION_KEY environment variable.");
        }

        try {
            byte[] decodedKey = Base64.getDecoder().decode(base64Key);
            if (decodedKey.length != 32) { // 256 bits = 32 bytes
                throw new IllegalArgumentException(
                        "Encryption key must be 32 bytes (256 bits). Use: openssl rand -base64 32");
            }
            this.secretKey = new SecretKeySpec(decodedKey, "AES");
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid encryption key format: " + e.getMessage(), e);
        }
    }

    /**
     * Encrypt plaintext using AES-256-GCM
     * 
     * @param plaintext The text to encrypt
     * @return Base64-encoded encrypted data (IV + ciphertext)
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
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, parameterSpec);

            // Encrypt
            byte[] ciphertext = cipher.doFinal(plaintext.getBytes("UTF-8"));

            // Combine IV + ciphertext
            ByteBuffer byteBuffer = ByteBuffer.allocate(iv.length + ciphertext.length);
            byteBuffer.put(iv);
            byteBuffer.put(ciphertext);

            // Encode to Base64
            return Base64.getEncoder().encodeToString(byteBuffer.array());

        } catch (Exception e) {
            throw new RuntimeException("Encryption failed: " + e.getMessage(), e);
        }
    }

    /**
     * Decrypt Base64-encoded encrypted data using AES-256-GCM
     * 
     * @param encryptedBase64 Base64-encoded encrypted data (IV + ciphertext)
     * @return Decrypted plaintext
     */
    public String decrypt(String encryptedBase64) {
        if (encryptedBase64 == null || encryptedBase64.isEmpty()) {
            return encryptedBase64;
        }

        try {
            // Decode from Base64
            byte[] decoded = Base64.getDecoder().decode(encryptedBase64);

            // Extract IV and ciphertext
            ByteBuffer byteBuffer = ByteBuffer.wrap(decoded);
            byte[] iv = new byte[GCM_IV_LENGTH];
            byteBuffer.get(iv);
            byte[] ciphertext = new byte[byteBuffer.remaining()];
            byteBuffer.get(ciphertext);

            // Initialize cipher
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            GCMParameterSpec parameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, parameterSpec);

            // Decrypt
            byte[] plaintext = cipher.doFinal(ciphertext);

            return new String(plaintext, "UTF-8");

        } catch (Exception e) {
            throw new RuntimeException("Decryption failed: " + e.getMessage(), e);
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
