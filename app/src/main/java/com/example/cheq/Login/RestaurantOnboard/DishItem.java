package com.example.cheq.Login.RestaurantOnboard;

import android.net.Uri;

public class DishItem {


    private String dishName;
    private String dishPrice;
    private String dishCategory;
    private Uri dishImageUri;


    public DishItem(String dishName, String dishPrice, String dishCategory, Uri dishImageUri) {
        this.dishImageUri = dishImageUri;
        this.dishCategory = dishCategory;
        this.dishName = dishName;
        this.dishPrice = dishPrice;
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

    public Uri getDishImageUri() {
        return dishImageUri;
    }

    public void setDishImageUri(Uri dishImageUri) {
        this.dishImageUri = dishImageUri;
    }

}
