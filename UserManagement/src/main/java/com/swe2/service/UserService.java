package com.swe2.service;

import com.swe2.model.Enum.Role;
import com.swe2.model.dto.RegisterResponse;
import com.swe2.model.dto.TokenValidationResponse;
import com.swe2.model.dto.UserCreateRequest;

import com.swe2.model.dto.changePasswordDTO;
import com.swe2.model.entity.User;
import com.swe2.repository.UserRepository;
import com.swe2.repository.tokenValidation;
import jakarta.validation.Valid;
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

    @Autowired
    private tokenValidation jwtValidation;

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

    @Transactional(readOnly = true)
    public List<User> getUnapprovedEmployees() {
        return userRepository.findByRoleAndApproved(Role.employee, false);
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

    public User approveUser(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));

        user.setApproved(true);

        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    public List<String> changePassword(@Valid changePasswordDTO request, String token) {
        TokenValidationResponse validationResponse = jwtValidation.validateToken(token);
        if (!validationResponse.isValid()) {
            return List.of("token: invalid token");
        }

        User user = userRepository.findById(validationResponse.getUserId());
        if (user == null) {
            return List.of("user: user not found");
        }
        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            return List.of("oldPassword: incorrect old password");
        }
        String newHashedPassword = passwordEncoder.encode(request.getNewPassword());
        user.setPassword(newHashedPassword);
        userRepository.save(user);
        return List.of();

    }
}