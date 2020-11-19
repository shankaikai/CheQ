package com.example.cheq.Login.RestaurantOnboard;

public class DishItem {

    private String dishTitle;
    private String dishPrice;
    private String dishImageUrl;
    private String dishCategory;

    // TODO: dishImageUrl
    public DishItem(String dishTitle, String dishPrice, String dishCategory) {
        this.dishTitle = dishTitle;
        this.dishPrice = dishPrice;
        this.dishCategory = dishCategory;
    }

    public DishItem(String dishTitle, String dishPrice, String dishCategory, String dishImageUrl) {
        this.dishTitle = dishTitle;
        this.dishPrice = dishPrice;
        this.dishCategory = dishCategory;
        this.dishImageUrl = dishImageUrl;
    }

    public String getTitle() {
        return dishTitle;
    }

    public void setTitle(String dishTitle) {
        this.dishTitle = dishTitle;
    }

    public String getPrice() {
        return dishPrice;
    }

    public void setPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getImageUrl() {
        return dishImageUrl;
    }

    public void setImageUrl(String dishImageUrl) {
        this.dishImageUrl = dishImageUrl;
    }

}
