package com.example.cheq_restaurant;

import java.util.ArrayList;

public class Seats {
    private String seatNo;
    private String noOfPax;

    public Seats(String seatNo, String noOfPax){
        this.seatNo = seatNo;
        this.noOfPax = noOfPax;
    }

    public String getSeatNo(){
        return seatNo;
    }

    public String getNoOfPax() {
        return noOfPax;
    }
}
