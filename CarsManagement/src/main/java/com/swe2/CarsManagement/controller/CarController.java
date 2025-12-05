package com.swe2.CarsManagement.controller;

import com.swe2.CarsManagement.dto.PhotoUrlRequest;
import com.swe2.CarsManagement.model.Car;
import com.swe2.CarsManagement.model.CarStatus;
import com.swe2.CarsManagement.service.CarService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @GetMapping
    public ResponseEntity<Page<Car>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(carService.getAllCars(page, size));
    }

    /**
     * Search cars by make, model, or year
     * Protected against XSS attacks with input sanitization
     * 
     * Example: GET /api/cars/search?q=Toyota&page=0&size=10
     * 
     * @param query Search query (will be sanitized)
     * @param page  Page number (default: 0)
     * @param size  Page size (default: 10, max: 50)
     * @return Page of matching cars
     */
    @GetMapping("/search")
    public ResponseEntity<Page<Car>> searchCars(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Validate query parameter
        if (query == null || query.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        // Limit query length (DoS protection)
        if (query.length() > 100) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        // Limit page size (DoS protection)
        if (size > 50) {
            size = 50;
        }

        // Search with XSS protection (handled in service layer)
        Page<Car> results = carService.searchCars(query, page, size);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return new ResponseEntity<>(carService.addCar(car), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.updateCar(id, car));
    }

    @PutMapping("/{id}/status/{status}")
    public ResponseEntity<Car> updateStatus(@PathVariable Long id,
            @PathVariable CarStatus status) {
        return ResponseEntity.ok(carService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photos")
    public ResponseEntity<Car> addPhotoUrl(@PathVariable Long id,
            @RequestBody PhotoUrlRequest request) {
        return ResponseEntity.ok(carService.addPhotoUrl(id, request.getUrl()));
    }

    @DeleteMapping("/{id}/photos")
    public ResponseEntity<Car> removePhotoUrl(@PathVariable Long id,
            @RequestBody PhotoUrlRequest request) {
        carService.removePhotoUrl(id, request.getUrl());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
