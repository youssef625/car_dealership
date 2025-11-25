// service/OfferService.java
package com.example.demo.service;

import com.example.demo.dto.CreateOfferRequest;
import com.example.demo.dto.OfferResponse;
import java.util.List;

public interface OfferService {
    OfferResponse createOffer(Long userId, CreateOfferRequest request);
    List<OfferResponse> getOffersByCar(Long carId);
    List<OfferResponse> getOffersByUser(Long userId);
    List<OfferResponse> getAllOffers();
    OfferResponse acceptOffer(Long offerId);
    OfferResponse rejectOffer(Long offerId);
}