package com.swe2.DTO;

public class carOfferForUser {
    int price;
    Long count;
    carOfferForUser(int price, Long count){
        this.price = price;
        this.count = count;
    }
    public int getPrice() {
        return price;
    }
    public Long getCount() {
        return count;
    }
}
