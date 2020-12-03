package com.example.cheq.Entities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class RestaurantInfoItemTest {

    RestaurantInfoItem restaurantInfoItem = new RestaurantInfoItem("88888888","rest","rest@gmail","rest.com","Bar");

    @Test
    public void getRestPhoneTest() {
        Assert.assertEquals("88888888", restaurantInfoItem.getRestPhone());
    }


    @Test
    public void setRestPhoneTest() {
        restaurantInfoItem.setRestPhone("88888889");
        Assert.assertEquals("88888889", restaurantInfoItem.getRestPhone());
    }

    @Test
    public void getRestNameTest() {
        Assert.assertEquals("rest", restaurantInfoItem.getRestName());
    }


    @Test
    public void setRestNameTest() {
        restaurantInfoItem.setRestName("rest1");
        Assert.assertEquals("rest1", restaurantInfoItem.getRestName());
    }

    @Test
    public void getRestEmailTest() {
        Assert.assertEquals("rest@gmail", restaurantInfoItem.getRestEmail());
    }


    @Test
    public void setRestEmailTest() {
        restaurantInfoItem.setRestEmail("rest1@gmail");
        Assert.assertEquals("rest1@gmail", restaurantInfoItem.getRestEmail());
    }

    @Test
    public void getRestImageUriTest() {
        Assert.assertEquals("rest.com", restaurantInfoItem.getRestImageUri());
    }


    @Test
    public void setRestImageUriTest() {
        restaurantInfoItem.setRestImageUri("rest1.com");
        Assert.assertEquals("rest1.com", restaurantInfoItem.getRestImageUri());
    }

    @Test
    public void getRestCategoryTest() {
        Assert.assertEquals("Bar", restaurantInfoItem.getRestCategory());
    }


    @Test
    public void setRestCategoryTest() {
        restaurantInfoItem.setRestCategory("Club");
        Assert.assertEquals("Club", restaurantInfoItem.getRestCategory());
    }

}