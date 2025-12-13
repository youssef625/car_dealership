package com.swe2.CarsManagement.client;

import com.swe2.CarsManagement.dto.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTHENTICATION")
public interface TokenValidationClient {

    @GetMapping("/api/auth/validate")
    TokenValidationResponse validateToken(@RequestHeader("Authorization") String authHeader);
}
