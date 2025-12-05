package com.swe2.util;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * JPA AttributeConverter for transparent email encryption/decryption
 * Automatically encrypts emails before saving to database
 * Automatically decrypts emails when loading from database
 */
@Converter
@Component
public class EmailEncryptionConverter implements AttributeConverter<String, String> {

    private static AESEncryptionUtil encryptionUtil;

    @Autowired
    public void setEncryptionUtil(AESEncryptionUtil encryptionUtil) {
        EmailEncryptionConverter.encryptionUtil = encryptionUtil;
    }

    /**
     * Encrypt email before storing in database
     */
    @Override
    public String convertToDatabaseColumn(String plainEmail) {
        if (plainEmail == null) {
            return null;
        }

        if (encryptionUtil == null) {
            throw new IllegalStateException("Encryption utility not initialized");
        }

        return encryptionUtil.encrypt(plainEmail);
    }

    /**
     * Decrypt email after loading from database
     */
    @Override
    public String convertToEntityAttribute(String encryptedEmail) {
        if (encryptedEmail == null) {
            return null;
        }

        if (encryptionUtil == null) {
            throw new IllegalStateException("Encryption utility not initialized");
        }

        return encryptionUtil.decrypt(encryptedEmail);
    }
}
