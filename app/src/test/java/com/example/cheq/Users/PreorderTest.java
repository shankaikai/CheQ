package com.example.cheq.Users;

import org.junit.Test;

import static org.junit.Assert.*;

public class PreorderTest {

    Preorder preorder = new Preorder(1, "a", "b", "c");

    @Test
    public void getRestaurantIDTest() {
        assertEquals("c", preorder.getRestaurantID());
    }

    @Test
    public void getDishQuantityTest() {
        assertEquals((Integer)1, preorder.getDishQuantity());
    }

    @Test
    public void getUserIDTest() {
        assertEquals("b", preorder.getUserID());
    }

    @Test
    public void getDishNameTest() {
        assertEquals("a", preorder.getDishName());
    }
}