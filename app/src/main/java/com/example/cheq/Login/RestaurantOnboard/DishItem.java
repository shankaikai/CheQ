package com.example.cheq.Login.RestaurantOnboard;

public class DishItem {

    private int dishID;
    private String dishTitle;
    private String dishPrice;
    private String dishImageUrl;

    // TODO: dishImageUrl
    public DishItem(int id, String dishTitle, String dishPrice) {
        this.dishID = id;
        this.dishTitle = dishTitle;
        this.dishPrice = dishPrice;
    }

    public DishItem(int id, String dishTitle, String dishPrice, String dishImageUrl) {
        this.dishID = id;
        this.dishTitle = dishTitle;
        this.dishPrice = dishPrice;
        this.dishImageUrl = dishImageUrl;
    }


    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
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
