package com.swe2.feigenRepo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "CARSMANAGEMENT")
public interface CarClient {

    @PutMapping("/api/cars/{id}/status/{status}")
    void updateCarStatus(@PathVariable("id") Long id, @PathVariable("status") String status,
            @org.springframework.web.bind.annotation.RequestHeader("Authorization") String token);

    @org.springframework.web.bind.annotation.GetMapping("/api/cars/{id}")
    com.swe2.DTO.CarDTO getCarById(@PathVariable("id") Long id);
}
