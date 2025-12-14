package com.swe2.controller;

import com.swe2.DTO.carOfferForUser;
import com.swe2.DTO.createOfferRequest;

import com.swe2.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;

    @PostMapping
    @com.swe2.aspect.RequiresRole({ "user", "employee" })
    public Object createOffer(@RequestBody createOfferRequest offer, @RequestHeader("Authorization") String token) {

        List<String> errors = offerService.createOffer(offer, token);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public Object getOfferById(@PathVariable Integer id) {

        carOfferForUser offer = offerService.getOfferById(id);
        if (offer == null) {
            return ResponseEntity.badRequest().body("Offer not found.");
        }
        return offerService.getOfferById(id);
    }

    @GetMapping("/admin/approve/{id}")
    @com.swe2.aspect.RequiresRole({ "superAdmin", "employee" })
    public Object approveOffer(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        List<String> errors = offerService.approveOffer(id, token);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/cancel/{carId}")
    @com.swe2.aspect.RequiresRole("superAdmin")
    public Object cancelOffer(@PathVariable Integer carId, @RequestHeader("Authorization") String token) {
        List<String> errors = offerService.cancelOffer(carId, token);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/confirm/{carId}")
    @com.swe2.aspect.RequiresRole("superAdmin")
    public Object confirmOffer(@PathVariable Integer carId, @RequestHeader("Authorization") String token) {
        List<String> errors = offerService.confirmOffer(carId, token);
        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/my-cars")
    @com.swe2.aspect.RequiresRole({ "user" })
    public Object getUserCars(@RequestParam(value = "type", required = false) String type,
            @RequestHeader("Authorization") String token) {
        try {
            List<com.swe2.DTO.UserCarResponse> cars = offerService.getUserCars(type, token);
            return ResponseEntity.ok(cars);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
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
