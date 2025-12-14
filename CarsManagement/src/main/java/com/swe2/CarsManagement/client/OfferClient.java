package com.swe2.CarsManagement.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "OFFERSMANAGEMENT")
public interface OfferClient {

    @GetMapping("/api/offers/{id}")
    OfferResponse getHighestOffer(@PathVariable("id") Long id,
            @RequestHeader(value = "Authorization", required = false) String token);

    class OfferResponse {
        private double price;
        private int count;

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
