package com.swe2.CarsManagement.dto;

import com.swe2.CarsManagement.model.Car;
import com.swe2.CarsManagement.model.CarStatus;
import java.util.List;

public class CarResponseDTO {

    private Long id;
    private String make;
    private String model;
    private int year;
    private double price;
    private String description;
    private CarStatus status;
    private List<String> images;
    private double lastOffer;

    public CarResponseDTO(Car car, double lastOffer) {
        this.id = car.getId();
        this.make = car.getMake();
        this.model = car.getModel();
        this.year = car.getYear();
        this.price = car.getPrice();
        this.description = car.getDescription();
        this.status = car.getStatus();
        this.images = car.getImages();
        this.lastOffer = lastOffer;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CarStatus getStatus() {
        return status;
    }

    public void setStatus(CarStatus status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public double getLastOffer() {
        return lastOffer;
    }

    public void setLastOffer(double lastOffer) {
        this.lastOffer = lastOffer;
    }
}
