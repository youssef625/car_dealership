package com.swe2.CarsManagement.repository;

import com.swe2.CarsManagement.model.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    /**
     * Search cars by make, model, or year
     * Uses parameterized queries to prevent SQL injection
     */
    @Query("SELECT c FROM Car c WHERE " +
            "LOWER(c.make) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(c.model) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "CAST(c.year AS string) LIKE CONCAT('%', :searchTerm, '%')")
    Page<Car> searchCars(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find cars by make (case-insensitive)
     */
    Page<Car> findByMakeContainingIgnoreCase(String make, Pageable pageable);

    /**
     * Find cars by model (case-insensitive)
     */
    Page<Car> findByModelContainingIgnoreCase(String model, Pageable pageable);
}
