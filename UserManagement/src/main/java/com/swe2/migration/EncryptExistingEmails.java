package com.swe2.migration;

import com.swe2.model.entity.User;
import com.swe2.repository.UserRepository;
import com.swe2.util.AESEncryptionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * One-time migration script to encrypt existing plain-text email addresses
 * Run with: mvn spring-boot:run -Dspring-boot.run.arguments=--migrate-emails
 * 
 * IMPORTANT: Put application in maintenance mode before running
 */
@Component
public class EncryptExistingEmails implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(EncryptExistingEmails.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AESEncryptionUtil encryptionUtil;

    @Override
    public void run(String... args) throws Exception {
        // Only run if --migrate-emails argument is provided
        boolean shouldMigrate = false;
        for (String arg : args) {
            if ("--migrate-emails".equals(arg)) {
                shouldMigrate = true;
                break;
            }
        }

        if (!shouldMigrate) {
            logger.info("Email migration not requested. Use --migrate-emails to run migration.");
            return;
        }

        logger.warn("=================================================");
        logger.warn("STARTING EMAIL ENCRYPTION MIGRATION");
        logger.warn("=================================================");

        migrateEmails();

        logger.warn("=================================================");
        logger.warn("EMAIL ENCRYPTION MIGRATION COMPLETED");
        logger.warn("=================================================");
    }

    @Transactional
    public void migrateEmails() {
        List<User> users = userRepository.findAll();

        logger.info("Found {} users to process", users.size());

        int encrypted = 0;
        int skipped = 0;
        int failed = 0;

        for (User user : users) {
            try {
                String email = user.getEmail();

                // Check if email is already encrypted (contains only Base64 characters and is
                // long)
                if (email != null && email.length() > 100 && email.matches("^[A-Za-z0-9+/=]+$")) {
                    logger.debug("User ID {} email already appears encrypted, skipping", user.getId());
                    skipped++;
                    continue;
                }

                // Email is plain text, encrypt it
                // Note: The JPA converter will automatically encrypt when saving
                // We just need to trigger a save
                logger.info("Encrypting email for user ID: {}", user.getId());

                // Force encryption by re-saving (converter will encrypt)
                // First, we need to disable the converter temporarily to get plain text
                // Then re-enable it for encryption
                // Actually, the converter is already active, so we just save
                userRepository.save(user);

                encrypted++;

            } catch (Exception e) {
                logger.error("Failed to encrypt email for user ID: {}", user.getId(), e);
                failed++;
            }
        }

        logger.info("Migration complete. Encrypted: {}, Skipped: {}, Failed: {}",
                encrypted, skipped, failed);

        if (failed > 0) {
            logger.error("Migration had {} failures. Check logs for details.", failed);
        }
    }
}
