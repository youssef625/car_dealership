package com.swe2.controller;

import com.swe2.DTO.createOfferRequest;
import com.swe2.DTO.offerbycar;
import com.swe2.model.Offer;
import com.swe2.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
@CrossOrigin(origins = "*")
public class OfferController {

    @Autowired
    private  OfferService offerService;


    @GetMapping
    public List<offerbycar> getAllOffers() {
        return List.of();
    }

    @PostMapping
    public Object createOffer(@RequestBody createOfferRequest offer , @RequestHeader("Authorization") String token) {

        List<String> errors = offerService.createOffer(offer , token);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(errors);
        }
        return ResponseEntity.ok().build();
    }






}
