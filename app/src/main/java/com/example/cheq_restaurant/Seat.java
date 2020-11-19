package com.example.cheq_restaurant;

import java.io.Serializable;

public class Seat implements Serializable {
    private String seatNo;
    private Integer noOfPax;

    public Seat(String seatNo, Integer noOfPax){
        this.seatNo = seatNo;
        this.noOfPax = noOfPax;
    }

    public String getSeatNo(){
        return seatNo;
    }

    public String getNoOfPax() {
        return noOfPax.toString() + " Pax";
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public void setNoOfPax(Integer noOfPax) {
        this.noOfPax = noOfPax;
    }
}

