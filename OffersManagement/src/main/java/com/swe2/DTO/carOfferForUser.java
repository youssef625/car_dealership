package com.swe2.DTO;

public class carOfferForUser {
    int price;
    Long count;

    public carOfferForUser(Number price, Number count) {
        this.price = price != null ? price.intValue() : 0;
        this.count = count != null ? count.longValue() : 0L;
    }

    public int getPrice() {
        return price;
    }

    public Long getCount() {
        return count;
    }
}
