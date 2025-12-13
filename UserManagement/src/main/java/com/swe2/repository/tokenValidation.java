package com.swe2.repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.swe2.model.dto.TokenValidationResponse;

@FeignClient(name = "AUTHENTICATION")
public interface tokenValidation {

    @GetMapping("/api/auth/validate")
    TokenValidationResponse validateToken(@RequestHeader("Authorization") String authHeader);

}
