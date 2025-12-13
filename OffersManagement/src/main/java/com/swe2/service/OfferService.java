package com.swe2.service;

import com.swe2.DTO.TokenValidationResponse;
import com.swe2.DTO.carOfferForUser;
import com.swe2.DTO.createOfferRequest;
import com.swe2.model.Offer;
import com.swe2.repository.OfferRepository;
import com.swe2.feigenRepo.tokenValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private tokenValidation tokenvalidation;

    @Autowired
    private com.swe2.feigenRepo.CarClient carClient;

    public List<String> createOffer(createOfferRequest offer, String token) {
        TokenValidationResponse validationResponse = tokenvalidation.validateToken(token);
        Offer offers = offerRepository.findTopByCarIdOrderByPriceDesc(offer.getCarId());

        if (offers != null && offer.getPrice() <= offers.getPrice()) {
            return List.of("Offer price must be higher than the current highest offer.");
        }
        // Token validation and role check handled by Aspect

        Offer newOffer = new Offer();
        newOffer.setCarId(offer.getCarId());
        newOffer.setPrice(offer.getPrice());
        newOffer.setUserId(validationResponse.getUserId());
        Offer savedOffer = offerRepository.save(newOffer);
        return List.of();
    }

    public carOfferForUser getOfferById(Integer id) {

        return offerRepository.findMaxPriceAndCountByCarId(id);
    }

    public List<String> approveOffer(int carId) {
        Offer offer = offerRepository.findTopByCarIdOrderByPriceDesc(carId);
        if (offer == null) {
            return List.of("No offers found for this car.");
        }
        offer.setStatus(com.swe2.model.OfferStatus.APPROVED);
        offerRepository.save(offer);
        try {
            carClient.updateCarStatus((long) carId, "RESERVED");
        } catch (Exception e) {
            return List.of("Failed to update car status: " + e.getMessage());
        }
        return List.of();
    }

    public List<String> cancelOffer(int offerId) {
        java.util.Optional<Offer> offerOptional = offerRepository.findById((long) offerId);
        if (offerOptional.isEmpty()) {
            return List.of("Offer not found.");
        }
        Offer offer = offerOptional.get();
        offer.setStatus(com.swe2.model.OfferStatus.CANCELLED);
        offerRepository.save(offer);
        try {
            carClient.updateCarStatus((long) offer.getCarId(), "AVAILABLE");
        } catch (Exception e) {
            return List.of("Failed to update car status: " + e.getMessage());
        }
        return List.of();
    }

    public List<String> confirmOffer(int offerId) {
        java.util.Optional<Offer> offerOptional = offerRepository.findById((long) offerId);
        if (offerOptional.isEmpty()) {
            return List.of("Offer not found.");
        }
        Offer offer = offerOptional.get();
        offer.setStatus(com.swe2.model.OfferStatus.CONFIRMED);
        offerRepository.save(offer);
        try {
            carClient.updateCarStatus((long) offer.getCarId(), "SOLD");
        } catch (Exception e) {
            return List.of("Failed to update car status: " + e.getMessage());
        }
        return List.of();
    }
}
