package com.swe2.feigenRepo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "CARSMANAGEMENT")
public interface CarClient {

    @PutMapping("/api/cars/{id}/status/{status}")
    void updateCarStatus(@PathVariable("id") Long id, @PathVariable("status") String status);
}
