package com.example.cheq.Entities;

public class FirebaseDishItem {
    private String dishName;
    private String dishPrice;
    private String dishCategory;
    private String downloadUrl;

    public FirebaseDishItem(String dishName, String dishPrice, String dishCategory, String downloadUrl) {
        this.dishName = dishName;
        this.dishPrice = dishPrice;
        this.dishCategory = dishCategory;
        this.downloadUrl = downloadUrl;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(String dishPrice) {
        this.dishPrice = dishPrice;
    }

    public String getDishCategory() {
        return dishCategory;
    }

    public void setDishCategory(String dishCategory) {
        this.dishCategory = dishCategory;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }
}
