package com.example.cheq.Entities;

import junit.framework.TestCase;

import org.junit.Assert;
import org.junit.Test;

public class CurrentQueueTest {

    CurrentQueue currentQueue = new CurrentQueue("4/12/2020",4L,3L,"88888888");

    @Test
    public void getDateTest() {
        Assert.assertEquals("4/12/2020", currentQueue.getDate());
    }

    @Test
    public void setDateTest() {
        currentQueue.setDate("5/12/2020");
        Assert.assertEquals("5/12/2020", currentQueue.getDate());
    }

    @Test
    public void getGroupSizeTest() {
        Assert.assertEquals((Long) 4L, currentQueue.getGroupSize());
    }

    @Test
    public void setGroupSizeTest() {
        currentQueue.setGroupSize(5L);
        Assert.assertEquals((Long) 5L, currentQueue.getGroupSize());
    }

    @Test
    public void getInFrontTest() {
        Assert.assertEquals((Long) 3L, currentQueue.getInfront());
    }

    @Test
    public void setInFrontTest() {
        currentQueue.setInfront(4L);
        Assert.assertEquals((Long) 4L, currentQueue.getInfront());
    }

    @Test
    public void getRestaurantIDTest() {
        Assert.assertEquals("88888888", currentQueue.getRestaurantID());
    }

    @Test
    public void setRestaurantIDTest() {
        currentQueue.setRestaurantID("88888889");
        Assert.assertEquals("88888889", currentQueue.getRestaurantID());
    }
}