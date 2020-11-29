package com.example.cheq.Restaurant;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatNo;
    private Integer noOfPax;
    private String userId;
    private String restaurantId;

    public Seat(String seatNo, Integer noOfPax, String userId, String restaurantId){
        this.seatNo = seatNo;
        this.noOfPax = noOfPax;
        this.userId = userId;
        this.restaurantId = restaurantId;
    }

    public String getSeatNo(){
        return seatNo;
    }

    public Integer getNoOfPaxInt() {return noOfPax;}

    public String getNoOfPax() {
        return noOfPax.toString() + " Pax";
    }

    public String getUserId() {
        return userId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setNoOfPax(Integer noOfPax) {
        this.noOfPax = noOfPax;
    }

    public void setUserId(String userId) {this.userId = userId;}

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }
}

