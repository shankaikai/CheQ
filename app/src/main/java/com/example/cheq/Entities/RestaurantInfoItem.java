package com.example.cheq.Entities;

public class RestaurantInfoItem {

    private String restPhone;
    private String restName;
    private String restEmail;
    private String restImageUri;
    private String restCategory;

    public RestaurantInfoItem(String restPhone, String restName, String restEmail, String restImageUri, String restCategory) {
        this.restPhone = restPhone;
        this.restName = restName;
        this.restEmail = restEmail;
        this.restImageUri = restImageUri;
        this.restCategory = restCategory;
    }

    public String getRestPhone() {
        return restPhone;
    }

    public void setRestPhone(String restPhone) {
        this.restPhone = restPhone;
    }

    public String getRestName() {
        return restName;
    }

    public void setRestName(String restName) {
        this.restName = restName;
    }

    public String getRestEmail() {
        return restEmail;
    }

    public void setRestEmail(String restEmail) {
        this.restEmail = restEmail;
    }

    public String getRestImageUri() {
        return restImageUri;
    }

    public void setRestImageUri(String restImageUri) {
        this.restImageUri = restImageUri;
    }

    public String getRestCategory() {
        return restCategory;
    }

    public void setRestCategory(String restCategory) {
        this.restCategory = restCategory;
    }

}
