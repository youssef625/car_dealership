package com.swe2.Authentication.service;

import com.swe2.Authentication.model.User;
import com.swe2.Authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Create a new user with encrypted password
     */
    public User createUser(String name, String email, String password, Integer roleId) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email " + email + " already exists");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRoleId(roleId);

        return userRepository.save(user);
    }

    /**
     * Find user by email
     */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * Find user by ID
     */
    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }

    /**
     * Get all users
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Get users by role
     */
    public List<User> getUsersByRole(Integer roleId) {
        return userRepository.findByRoleId(roleId);
    }

    /**
     * Ban or unban a user
     */
    public User updateBanStatus(Integer userId, Boolean isBanned) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.setIsBanned(isBanned);
        return userRepository.save(user);
    }

    /**
     * Update user password
     */
    public User updatePassword(Integer userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));

        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    /**
     * Delete user
     */
    public void deleteUser(Integer userId) {
        userRepository.deleteById(userId);
    }

    /**
     * Check if user is banned
     */
    public boolean isUserBanned(String email) {
        return userRepository.findByEmail(email)
                .map(User::getIsBanned)
                .orElse(false);
    }
}
