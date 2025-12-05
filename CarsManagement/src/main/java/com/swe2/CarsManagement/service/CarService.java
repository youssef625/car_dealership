package com.swe2.CarsManagement.service;

import com.swe2.CarsManagement.model.Car;
import com.swe2.CarsManagement.model.CarStatus;
import com.swe2.CarsManagement.repository.CarRepository;
import com.swe2.CarsManagement.util.XSSProtectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private XSSProtectionUtil xssProtection;

    public Page<Car> getAllCars(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.findAll(pageable);
    }

    /**
     * Search cars by make, model, or year with XSS protection
     * 
     * @param searchTerm The search query (will be sanitized)
     * @param page       Page number
     * @param size       Page size
     * @return Page of matching cars
     */
    public Page<Car> searchCars(String searchTerm, int page, int size) {
        // Sanitize search term to prevent XSS attacks
        String sanitizedTerm = xssProtection.sanitizeSearchQuery(searchTerm);

        // Use parameterized query (protected against SQL injection)
        Pageable pageable = PageRequest.of(page, size);
        return carRepository.searchCars(sanitizedTerm, pageable);
    }

    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    public Car addCar(Car car) {
        return carRepository.save(car);
    }

    public Car updateCar(Long id, Car updatedCar) {
        return carRepository.findById(id).map(car -> {
            car.setMake(updatedCar.getMake());
            car.setModel(updatedCar.getModel());
            car.setYear(updatedCar.getYear());
            car.setPrice(updatedCar.getPrice());
            car.setDescription(updatedCar.getDescription());
            car.setStatus(updatedCar.getStatus());
            car.setImages(updatedCar.getImages());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }

    public void deleteCar(Long id) {
        carRepository.deleteById(id);
    }

    public Car updateStatus(Long id, CarStatus status) {
        Car car = carRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Car not found"));

        car.setStatus(status);
        return carRepository.save(car);
    }

    public Car addPhotoUrl(Long carId, String photoUrl) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + carId));

        if (car.getImages() == null) {
            car.setImages(new ArrayList<>());
        }

        car.getImages().add(photoUrl);
        return carRepository.save(car);
    }

    public Car removePhotoUrl(Long carId, String photoUrl) {
        Car car = carRepository.findById(carId)
                .orElseThrow(() -> new RuntimeException("Car not found with id " + carId));

        if (car.getImages() != null) {
            car.getImages().remove(photoUrl);
            return carRepository.save(car);
        }

        return car;
    }
}
