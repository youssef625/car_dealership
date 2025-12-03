package com.swe2.CarsManagement.dto;

public class PhotoUrlRequest {

    private String url;

    public PhotoUrlRequest() {
    }

    public PhotoUrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
