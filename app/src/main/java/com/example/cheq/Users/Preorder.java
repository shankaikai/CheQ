package com.example.cheq.Users;

public class Preorder {
    private String userID;
    private String restaurantID;
    private Integer dishQuantity;
    private String dishName;

    public Preorder(Integer dishCount, String dishName, String userID, String restaurantID) {
        this.dishQuantity = 1;
        this.userID = userID;
        this.restaurantID = restaurantID;
        this.dishName = dishName;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public Integer getDishQuantity() {
        return dishQuantity;
    }

    public String getUserID() {
        return userID;
    }

    public String getDishName() {
        return dishName;
    }
}