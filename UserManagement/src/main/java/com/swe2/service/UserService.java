package com.swe2.service;

import com.swe2.model.Enum.Role;
import com.swe2.model.dto.RegisterResponse;
import com.swe2.model.dto.UserCreateRequest;

import com.swe2.model.entity.User;
import com.swe2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public Page<User> getAllUsers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));
    }

    @Transactional(readOnly = true)
    public List<User> getUsersByRole(Role role) {
        return userRepository.findByRole(role);
    }

    @Transactional
    public RegisterResponse createUser(UserCreateRequest request) {

        Optional<User> existingUserOpt = userRepository.findByEmail(request.getEmail());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            if (existingUser.isBanned()) { // adjust getter if your entity uses a different name
                return new RegisterResponse(List.of("email: user is banned"));
            }
            return new RegisterResponse(List.of("email: user already registered"));
        }

        Role role = request.getRoleId();

        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // Create user
        User user = new User(request.getName(), request.getEmail(), hashedPassword, role);
        User savedUser = userRepository.save(user);

        return new RegisterResponse(savedUser);
    }

    @Transactional
    public User banUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setBanned(true);

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public User unBanUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setBanned(false);

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public User resetPassword(Integer id, String newPassword) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        String hashedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(hashedPassword);

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public User approveUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setApproved(true);

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }
}