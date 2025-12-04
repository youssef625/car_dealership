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
    private  OfferRepository offerRepository;

    @Autowired
    private  tokenValidation tokenvalidation;




    public List<String> createOffer(createOfferRequest offer, String token) {
        TokenValidationResponse validationResponse = tokenvalidation.validateToken(token);
        if (!validationResponse.isValid()) {
            return List.of("Invalid token. Offer creation failed.");
        }
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
}

