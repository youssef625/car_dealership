package com.swe2.controller;

import com.swe2.model.dto.RegisterResponse;
import com.swe2.model.dto.UserCreateRequest;
import com.swe2.model.Enum.Role;
import com.swe2.model.dto.UserDTO;
import com.swe2.model.dto.changePasswordDTO;
import com.swe2.model.entity.User;
import com.swe2.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @GetMapping
    @com.swe2.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Page<UserDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (size > 50) {
            size = 50;
        } else if (size > 0) {
            size = 0;
        }

        if (page < 0) {
            page = 0;
        }
        Page<User> users = userService.getAllUsers(page, size);
        Page<UserDTO> userDTOs = users.map(user -> new UserDTO(
                user));
        return ResponseEntity.ok(userDTOs);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(new UserDTO(user));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/authdata/{email}")
    public ResponseEntity<User> getAuthData(@PathVariable String email) {
        try {
            User user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/approve/{id}")
    @com.swe2.aspect.RequiresRole("superAdmin")
    public ResponseEntity<User> approveUser(@PathVariable Integer id) {
        try {
            User user = userService.approveUser(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest request, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // Return all validation errors as a list of messages
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getField() + ": " + error.getDefaultMessage())
                    .collect(Collectors.toList());

            return ResponseEntity.status(HttpStatus.CREATED).body(new RegisterResponse(errors));
        }
        try {
            RegisterResponse user = userService.createUser(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (RuntimeException e) {
            logger.error("Error creating user: {}", e.getMessage(), e);
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
            logger.error("Validation error - Field: {}, Message: {}", fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("User does not have permission") || ex.getMessage().contains("Invalid token")
                || ex.getMessage().contains("Missing or invalid Authorization header")) {
            return ResponseEntity.status(403).body(ex.getMessage());
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @GetMapping("/ban/{id}")
    @com.swe2.aspect.RequiresRole("superAdmin")
    public ResponseEntity<User> ban(
            @PathVariable Integer id) {
        try {
            User user = userService.banUser(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/unban/{id}")
    @com.swe2.aspect.RequiresRole("superAdmin")
    public ResponseEntity<User> unBan(
            @PathVariable Integer id) {
        try {
            User user = userService.unBanUser(id);
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/changePassword/")
    public Object changePassword(@Valid @RequestBody changePasswordDTO request,
            @RequestHeader("Authorization") String token,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasErrors())
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getFieldErrors());

            List<String> errors = userService.changePassword(request, token);

            if (errors.isEmpty()) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
            }

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
