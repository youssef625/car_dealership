package com.swe2.Authentication.repository;

import com.swe2.Authentication.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-management")
public interface UserRepository {

    @GetMapping("/api/users/email/{email}")
    User findByEmail(@PathVariable("email") String email);

    @GetMapping("/api/users/{id}")
    User findById(@PathVariable("id") Integer id);
}