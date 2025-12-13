// dto/CreateOfferRequest.java
package com.example.demo.dto;

public class CreateOfferRequest {
    private Long carId;
    private Double amount;

    // Getters and Setters
    public Long getCarId() { return carId; }
    public void setCarId(Long carId) { this.carId = carId; }
    
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
}