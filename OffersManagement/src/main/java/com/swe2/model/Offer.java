package com.swe2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int offerId;

    @Column(nullable = false)
    private int carId;

    @Column(nullable = false)
    private int userId;

    @Column(nullable = false)
    private int price;

    @Column(nullable = true)
    private int employeeId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OfferStatus status;

    public Offer() {
        this.status = OfferStatus.PENDING;
    }

    public Offer(int carId, int userId, int price, int employeeId) {
        this.carId = carId;
        this.userId = userId;
        this.price = price;
        this.employeeId = employeeId;
        this.status = OfferStatus.PENDING;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public OfferStatus getStatus() {
        return status;
    }

    public void setStatus(OfferStatus status) {
        this.status = status;
    }
}
