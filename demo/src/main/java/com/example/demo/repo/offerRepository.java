// repository/OfferRepository.java
package com.example.demo.repository;

import com.example.demo.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByCarId(Long carId);
    List<Offer> findByUserId(Long userId);
}