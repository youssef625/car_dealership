package com.swe2.CarsManagement.model;

import javax.persistence.*;

@Entity
public class CarImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public CarImage() {}

    public CarImage(String imageUrl, Car car) {
        this.imageUrl = imageUrl;
        this.car = car;
    }

}
