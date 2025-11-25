// controller/OfferController.java
package com.example.demo.controller;

import com.example.demo.dto.CreateOfferRequest;
import com.example.demo.dto.OfferResponse;
import com.example.demo.model.Enum.Role;
import com.example.demo.service.OfferService;
import com.example.demo.service.UserManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    @Autowired
    private OfferService offerService;
    
    @Autowired
    private UserManagement userManagement;

    @PostMapping
    public ResponseEntity<OfferResponse> createOffer(@RequestBody CreateOfferRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var user = userManagement.getCurrentUser(username);
        
        OfferResponse response = offerService.createOffer(user.getId(), request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<OfferResponse>> getOffersByCar(@PathVariable Long carId) {
        List<OfferResponse> offers = offerService.getOffersByCar(carId);
        return ResponseEntity.ok(offers);
    }

    @GetMapping("/my-offers")
    public ResponseEntity<List<OfferResponse>> getMyOffers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var user = userManagement.getCurrentUser(username);
        
        List<OfferResponse> offers = offerService.getOffersByUser(user.getId());
        return ResponseEntity.ok(offers);
    }

    @GetMapping
    public ResponseEntity<List<OfferResponse>> getAllOffers() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var user = userManagement.getCurrentUser(username);
        
        // Only ADMIN and EMPLOYEE can view all offers
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.EMPLOYEE) {
            return ResponseEntity.status(403).build();
        }
        
        List<OfferResponse> offers = offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }

    @PutMapping("/{id}/accept")
    public ResponseEntity<OfferResponse> acceptOffer(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var user = userManagement.getCurrentUser(username);
        
        // Only ADMIN and EMPLOYEE can accept offers
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.EMPLOYEE) {
            return ResponseEntity.status(403).build();
        }
        
        OfferResponse response = offerService.acceptOffer(id);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<OfferResponse> rejectOffer(@PathVariable Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        var user = userManagement.getCurrentUser(username);
        
        // Only ADMIN and EMPLOYEE can reject offers
        if (user.getRole() != Role.ADMIN && user.getRole() != Role.EMPLOYEE) {
            return ResponseEntity.status(403).build();
        }
        
        OfferResponse response = offerService.rejectOffer(id);
        return ResponseEntity.ok(response);
    }
}