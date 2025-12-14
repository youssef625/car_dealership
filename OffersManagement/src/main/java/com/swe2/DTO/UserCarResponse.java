package com.swe2.DTO;

public class UserCarResponse {
    private CarDTO car;
    private boolean isWinning;
    private int myOfferPrice;

    public UserCarResponse(CarDTO car, boolean isWinning, int myOfferPrice) {
        this.car = car;
        this.isWinning = isWinning;
        this.myOfferPrice = myOfferPrice;
    }

    public CarDTO getCar() {
        return car;
    }

    public void setCar(CarDTO car) {
        this.car = car;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public void setWinning(boolean winning) {
        isWinning = winning;
    }

    public int getMyOfferPrice() {
        return myOfferPrice;
    }

    public void setMyOfferPrice(int myOfferPrice) {
        this.myOfferPrice = myOfferPrice;
    }
}
