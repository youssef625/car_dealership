package com.swe2.feigenRepo;

import com.swe2.DTO.TokenValidationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "AUTHENTICATION")
public interface tokenValidation {

    @PostMapping("/api/auth/validateToken")
    TokenValidationResponse validateToken(@RequestBody String token);


}
