package com.swe2.repository;

import com.swe2.DTO.carOfferForUser;
import com.swe2.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    // Just test if the query logic is valid
    @Query(value = "SELECT COALESCE(MAX(price), 0) AS price, COUNT(*) AS count " +
            "FROM offers " + // This is your actual TABLE name
            "WHERE car_id = :carId", // This is your actual COLUMN name
            nativeQuery = true)
    carOfferForUser findMaxPriceAndCountByCarId(@Param("carId") int carId);

    Offer findTopByCarIdOrderByPriceDesc(int carId);
}
