package com.swe2.CarsManagement.controller;

import com.swe2.CarsManagement.model.Offer;
import com.swe2.CarsManagement.service.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {

    private final OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/car/{carId}")
    public ResponseEntity<List<Offer>> getOffersByCar(@PathVariable Long carId) {
        return ResponseEntity.ok(offerService.getOffersByCar(carId));
    }

    @PostMapping("/accept/{offerId}")
    public ResponseEntity<Offer> acceptOffer(@PathVariable Long offerId) {
        return ResponseEntity.ok(offerService.acceptOffer(offerId));
    }
}
