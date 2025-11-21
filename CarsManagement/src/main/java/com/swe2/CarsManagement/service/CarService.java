package com.swe2.CarsManagement.service;

import com.swe2.CarsManagement.model.Car;
import com.swe2.CarsManagement.repository.CarRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public List<Car> getAllCars() { return carRepository.findAll(); }

    public Optional<Car> getCarById(Long id) { return carRepository.findById(id); }

    public Car addCar(Car car) { return carRepository.save(car); }

    public Car updateCar(Long id, Car updatedCar) {
        return carRepository.findById(id).map(car -> {
            car.setMake(updatedCar.getMake());
            car.setModel(updatedCar.getModel());
            car.setYear(updatedCar.getYear());
            car.setPrice(updatedCar.getPrice());
            car.setDescription(updatedCar.getDescription());
            car.setAvailable(updatedCar.isAvailable());
            return carRepository.save(car);
        }).orElseThrow(() -> new RuntimeException("Car not found with id " + id));
    }

    public void deleteCar(Long id) { carRepository.deleteById(id); }
}
