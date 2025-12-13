package com.swe2.config;

import com.swe2.model.Enum.Role;
import com.swe2.model.entity.User;
import com.swe2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        String email = "admin@cardealership.com";
        Optional<User> userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) {
            User admin = new User();
            admin.setName("Super Admin");
            admin.setEmail(email);
            admin.setPassword(passwordEncoder.encode("password"));
            admin.setRole(Role.superAdmin);
            admin.setApproved(true);
            admin.setBanned(false);

            userRepository.save(admin);
            System.out.println("Super Admin user seeded: " + email);
        } else {
            System.out.println("Super Admin user already exists.");
        }
    }
}
