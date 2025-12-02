package com.swe2.Authentication.controller;

import com.swe2.Authentication.model.*;
import com.swe2.Authentication.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Configure properly in production
public class AuthController {

    @Autowired
    private AuthService authService;



    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {
        try {
            LoginResponse response = authService.login(request);
            if(response.hasErrors()){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response.getErrors());
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }


    @GetMapping("/validate")
    public ResponseEntity<TokenValidationResponse> validateToken(
            @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.replace("Bearer ", "");
            TokenValidationResponse response = authService.validateToken(token);

            if (response.isValid()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new TokenValidationResponse(false, "Invalid authorization header"));
        }
    }
    @PostMapping("/register")
    public Object register(@RequestBody RegisterRequest request){
        try {
            RegisterResponse response = authService.register(request);
            if(!response.hasErrors()) return ResponseEntity.ok(response);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getErrors());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

    @PostMapping("admin/register")
    public Object registerAdmin(@RequestBody RegisterRequest request){
        try {
            RegisterResponse response = authService.registerAdmin(request);
            if(!response.hasErrors()) return ResponseEntity.ok(response);

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.getErrors());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

    }

   }