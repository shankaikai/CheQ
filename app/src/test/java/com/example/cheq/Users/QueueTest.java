package com.example.cheq.Users;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueueTest {

    Queue q = new Queue(2, "a", "10102011");

    @Test
    public void getRestaurantIDTest() {
        assertEquals("a", q.getRestaurantID());
    }

    @Test
    public void getGroupSizeTest() {
        assertEquals((Integer)2, q.getGroupSize());
    }

    @Test
    public void getDateTest() {
        assertEquals("10102011", q.getDate());
    }
}