package com.swe2.CarsManagement.model;

import jakarta.persistence.*;

@Entity
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double amount;
    private String status = "PENDING";
    private Long userId;

    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;

    public Offer() {}

    public Offer(Long id, double amount, String status, Long userId, Car car) {
        this.id = id;
        this.amount = amount;
        this.status = status;
        this.userId = userId;
        this.car = car;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public Car getCar() { return car; }
    public void setCar(Car car) { this.car = car; }
}
