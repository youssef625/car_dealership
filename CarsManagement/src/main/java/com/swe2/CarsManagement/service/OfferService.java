package com.swe2.CarsManagement.service;

import com.swe2.CarsManagement.model.Offer;
import com.swe2.CarsManagement.model.Car;
import com.swe2.CarsManagement.repository.OfferRepository;
import com.swe2.CarsManagement.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final CarRepository carRepository;

    public OfferService(OfferRepository offerRepository, CarRepository carRepository) {
        this.offerRepository = offerRepository;
        this.carRepository = carRepository;
    }

    public List<Offer> getOffersByCar(Long carId) {
        return offerRepository.findByCarId(carId);
    }

    public Offer acceptOffer(Long offerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        // Reject 
        List<Offer> otherOffers = offerRepository.findByCarId(offer.getCar().getId());
        for (Offer o : otherOffers) {
            if (!o.getId().equals(offerId)) {
                o.setStatus("REJECTED");
                offerRepository.save(o);
            }
        }

        // Accept 
        offer.setStatus("ACCEPTED");
        offerRepository.save(offer);

        // unavailable
        Car car = offer.getCar();
        car.setAvailable(false);
        carRepository.save(car);

        return offer;
    }
}
