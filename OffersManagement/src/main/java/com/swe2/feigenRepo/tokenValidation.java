package com.swe2.feigenRepo;

import com.swe2.DTO.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "AUTHENTICATION")
public interface tokenValidation {

    @GetMapping("/api/auth/validate")
    TokenValidationResponse validateToken(@RequestHeader("Authorization") String authHeader);

}
