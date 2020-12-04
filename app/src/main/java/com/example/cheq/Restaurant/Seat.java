package com.example.cheq.Restaurant;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatNo;
    private Integer noOfPax;
    private String userId;
    private String restaurantId;
    private  String seatId;

    public Seat(String seatNo, Integer noOfPax, String userId, String restaurantId, String seatId){
        this.seatNo = seatNo;
        this.noOfPax = noOfPax;
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.seatId = seatId;
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

    public String getSeatId() {
        return seatId;
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

    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}

