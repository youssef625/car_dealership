package com.swe2.CarsManagement.repository;

import com.swe2.CarsManagement.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByCarId(Long carId);
}
