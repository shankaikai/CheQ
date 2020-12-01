package com.example.cheq.Users;

public class Queue {
    private Integer groupSize;
    private String date;
    private String restaurantID;

    public Queue(Integer size, String restID, String date) {
        this.restaurantID = restID;
        this.date = date;
        this.groupSize = size;
    }

    public String getRestaurantID() {
        return restaurantID;
    }

    public Integer getGroupSize() {
        return groupSize;
    }

    public String getDate() {
        return date;
    }
}