package com.swe2.service;

import com.swe2.model.Offer;
import com.swe2.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;

    @Autowired
    public OfferService(OfferRepository offerRepository) {
        this.offerRepository = offerRepository;
    }

    public List<Offer> getAllOffers() {
        return offerRepository.findAll();
    }

    public Optional<Offer> getOfferById(Long id) {
        return offerRepository.findById(id);
    }

    public Offer createOffer(Offer offer) {
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Long id, Offer offerDetails) {
        return offerRepository.findById(id).map(offer -> {
            offer.setCarId(offerDetails.getCarId());
            offer.setUserId(offerDetails.getUserId());
            offer.setPrice(offerDetails.getPrice());
            offer.setEmployeeId(offerDetails.getEmployeeId());
            return offerRepository.save(offer);
        }).orElseThrow(() -> new RuntimeException("Offer not found with id " + id));
    }

    public void deleteOffer(Long id) {
        offerRepository.deleteById(id);
    }
}
