// service/impl/OfferServiceImpl.java
package com.example.demo.service.impl;

import com.example.demo.dto.CreateOfferRequest;
import com.example.demo.dto.OfferResponse;
import com.example.demo.model.entity.*;
import com.example.demo.repository.OfferRepository;
import com.example.demo.repository.CarRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferServiceImpl implements OfferService {

    @Autowired
    private OfferRepository offerRepository;

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public OfferResponse createOffer(Long userId, CreateOfferRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(() -> new RuntimeException("Car not found"));

        if (!car.getAvailable()) {
            throw new RuntimeException("Car is not available for offers");
        }

        Offer offer = new Offer();
        offer.setUser(user);
        offer.setCar(car);
        offer.setAmount(request.getAmount());

        return toDTO(offerRepository.save(offer));
    }

    @Override
    public List<OfferResponse> getOffersByCar(Long carId) {
        return offerRepository.findByCarId(carId)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public List<OfferResponse> getOffersByUser(Long userId) {
        return offerRepository.findByUserId(userId)
                .stream().map(this::toDTO).toList();
    }

    @Override
    public List<OfferResponse> getAllOffers() {
        return offerRepository.findAll()
                .stream().map(this::toDTO).toList();
    }

    @Override
    public OfferResponse acceptOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        
        offer.setStatus(OfferStatus.ACCEPTED);
        
        // Mark car as unavailable when offer is accepted
        Car car = offer.getCar();
        car.setAvailable(false);
        carRepository.save(car);
        
        return toDTO(offerRepository.save(offer));
    }

    @Override
    public OfferResponse rejectOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));
        
        offer.setStatus(OfferStatus.REJECTED);
        return toDTO(offerRepository.save(offer));
    }

    private OfferResponse toDTO(Offer offer) {
        OfferResponse dto = new OfferResponse();
        dto.setId(offer.getId());
        dto.setUserId(offer.getUser().getId());
        dto.setUsername(offer.getUser().getUsername());
        dto.setCarId(offer.getCar().getId());
        dto.setCarBrand(offer.getCar().getBrand());
        dto.setCarModel(offer.getCar().getModel());
        dto.setAmount(offer.getAmount());
        dto.setStatus(offer.getStatus());
        dto.setCreatedAt(offer.getCreatedAt());
        return dto;
    }
}