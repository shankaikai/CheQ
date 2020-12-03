package com.example.cheq.Restaurant;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class SeatTest {

    // String seatNo, Integer noOfPax, String userId, String restaurantId, String seatId
    Seat seat = new Seat("1",5,"88888888","88888889","A1");

    @Test
    public void getSeatNoTest() {
        Assert.assertEquals("1", seat.getSeatNo());
    }

    @Test
    public void setSeatNoTest() {
        seat.setSeatNo("2");
        Assert.assertEquals("2", seat.getSeatNo());
    }

    @Test
    public void getNoOfPaxTest() {
        Assert.assertEquals("5 Pax", seat.getNoOfPax());
    }

    @Test
    public void setNoOfPaxTest() {
        seat.setNoOfPax(6);
        Assert.assertEquals("6 Pax", seat.getNoOfPax());
    }

    @Test
    public void getUserIdTest() {
        Assert.assertEquals("88888888", seat.getUserId());
    }

    @Test
    public void setUserIdTest() {
        seat.setUserId("98888888");
        Assert.assertEquals("98888888", seat.getUserId());
    }

    @Test
    public void getRestaurantIdTest() {
        Assert.assertEquals("88888889", seat.getRestaurantId());
    }

    @Test
    public void setRestaurantIdTest() {
        seat.setRestaurantId("98888889");
        Assert.assertEquals("98888889", seat.getRestaurantId());
    }

    @Test
    public void getSeatIdTest() {
        Assert.assertEquals("A1", seat.getSeatId());
    }

    @Test
    public void setSeatIdTest() {
        seat.setSeatId("B2");
        Assert.assertEquals("B2", seat.getSeatId());
    }
}