package com.example.cheq.Entities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class FirebaseDishItemTest {

    FirebaseDishItem firebaseDishItem = new FirebaseDishItem("Curry","5","Sauce","curry.com");

    @Test
    public void getDishNameTest() {
        Assert.assertEquals("Curry", firebaseDishItem.getDishName());
    }

    @Test
    public void setDishNameTest() {
        firebaseDishItem.setDishName("Soup");
        Assert.assertEquals("Soup", firebaseDishItem.getDishName());
    }

    @Test
    public void getDishPriceTest() {
        Assert.assertEquals("5", firebaseDishItem.getDishPrice());
    }

    @Test
    public void setDishPriceTest() {
        firebaseDishItem.setDishPrice("6");
        Assert.assertEquals("6", firebaseDishItem.getDishPrice());
    }

    @Test
    public void getDishCategoryTest() {
        Assert.assertEquals("Sauce", firebaseDishItem.getDishCategory());
    }

    @Test
    public void setDishCategoryTest() {
        firebaseDishItem.setDishCategory("Soup");
        Assert.assertEquals("Soup", firebaseDishItem.getDishCategory());
    }

    @Test
    public void getDownloadUrlTest() {
        Assert.assertEquals("curry.com", firebaseDishItem.getDownloadUrl());
    }

    @Test
    public void setDownloadUrlTest() {
        firebaseDishItem.setDownloadUrl("soup.com");
        Assert.assertEquals("soup.com", firebaseDishItem.getDownloadUrl());
    }
}