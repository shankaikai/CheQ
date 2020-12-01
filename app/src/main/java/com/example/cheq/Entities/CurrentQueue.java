package com.example.cheq.Entities;

public class CurrentQueue {
    private String date, restaurantID;
    private Long groupSize, infront;

    public CurrentQueue() {

    }

    public CurrentQueue(String date, Long groupSize, Long infront, String restaurantID) {
        this.date = date;
        this.groupSize = groupSize;
        this.infront = infront;
        this.restaurantID = restaurantID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Long getGroupSize() {
        return groupSize;
    }

    public void setGroupSize(Long groupSize) {
        this.groupSize = groupSize;
    }

    public Long getInfront() {
        return infront;
    }

    public void setInfront(Long infront) {
        this.infront = infront;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(String restaurantID) {
        this.restaurantID = restaurantID;
    }
}
