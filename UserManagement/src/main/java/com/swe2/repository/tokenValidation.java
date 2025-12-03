package com.swe2.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import  com.swe2.model.dto.TokenValidationResponse;

@FeignClient(name = "AUTHENTICATION")
public interface tokenValidation {

    @PostMapping("/api/auth/validateToken")
    TokenValidationResponse validateToken(@RequestBody String token);


}
