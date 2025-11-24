package com.swe2.CarsManagement.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String make;
    private String model;
    private int year;
    private double price;
    private String description;

    @Enumerated(EnumType.STRING)
    private CarStatus status = CarStatus.AVAILABLE;

    @ElementCollection
    private List<String> images; 

    public Car() {}

    public Car(Long id, String make, String model, int year, double price,
               String description, CarStatus status, List<String> images) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.price = price;
        this.description = description;
        this.status = status;
        this.images = images;
    }

    // Getters & setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getMake() { return make; }
    public void setMake(String make) { this.make = make; }

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public CarStatus getStatus() { return status; }
    public void setStatus(CarStatus status) { this.status = status; }

    public List<String> getImages() { return images; }
    public void setImages(List<String> images) { this.images = images; }
}
