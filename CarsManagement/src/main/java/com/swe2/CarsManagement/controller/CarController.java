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

@RestController
@RequestMapping("/api/cars")
public class CarController {

    @Autowired
    private CarService carService;

    @Autowired
    private com.swe2.CarsManagement.client.TokenValidationClient tokenValidationClient;

    @GetMapping
    public ResponseEntity<Page<Car>> getAllCars(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestHeader(value = "Authorization", required = false) String token) {

        String role = "GUEST";
        if (token != null && token.startsWith("Bearer ")) {
            try {
                com.swe2.CarsManagement.dto.TokenValidationResponse response = tokenValidationClient
                        .validateToken(token);
                if (response.isValid()) {
                    role = response.getRole();
                }
            } catch (Exception e) {
                // Ignore invalid token, treat as GUEST
            }
        }

        return ResponseEntity.ok(carService.getAllCars(page, size, role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id) {
        return carService.getCarById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @com.swe2.CarsManagement.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        return new ResponseEntity<>(carService.addCar(car), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @com.swe2.CarsManagement.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        return ResponseEntity.ok(carService.updateCar(id, car));
    }

    @PutMapping("/{id}/status/{status}")
    @com.swe2.CarsManagement.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Car> updateStatus(@PathVariable Long id,
            @PathVariable CarStatus status) {
        return ResponseEntity.ok(carService.updateStatus(id, status));
    }

    @DeleteMapping("/{id}")
    @com.swe2.CarsManagement.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/photos")
    @com.swe2.CarsManagement.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Car> addPhotoUrl(@PathVariable Long id,
            @RequestBody PhotoUrlRequest request) {
        return ResponseEntity.ok(carService.addPhotoUrl(id, request.getUrl()));
    }

    @DeleteMapping("/{id}/photos")
    @com.swe2.CarsManagement.aspect.RequiresRole({ "superAdmin", "employee" })
    public ResponseEntity<Car> removePhotoUrl(@PathVariable Long id,
            @RequestBody PhotoUrlRequest request) {
        carService.removePhotoUrl(id, request.getUrl());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        if (ex.getMessage().contains("User does not have permission") || ex.getMessage().contains("Invalid token")
                || ex.getMessage().contains("Missing or invalid Authorization header")) {
            return ResponseEntity.status(403).body(ex.getMessage());
        }
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
