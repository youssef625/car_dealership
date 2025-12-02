package com.swe2.Authentication.repository;

import com.swe2.Authentication.model.RegisterRequest;
import com.swe2.Authentication.model.RegisterResponse;
import com.swe2.Authentication.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USERMANAGEMENT")
public interface UserRepository {

    @GetMapping("/api/users/authdata/{email}")
    User findByEmail(@PathVariable("email") String email);

    @GetMapping("/api/users/{id}")
    User findById(@PathVariable("id") Integer id);

    @PostMapping("/api/users/")
    RegisterResponse registerUser(@RequestBody RegisterRequest request);
}