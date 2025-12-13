package com.swe2.CarsManagement.repository;

import com.swe2.CarsManagement.model.Car;
import com.swe2.CarsManagement.model.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findByStatus(CarStatus status, Pageable pageable);
}
