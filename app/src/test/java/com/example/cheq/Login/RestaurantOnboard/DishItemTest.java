package com.example.cheq.Login.RestaurantOnboard;

import android.net.Uri;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
public class DishItemTest {

    DishItem dishItem = new DishItem("rice", "5.00", "Main", Uri.parse("www.google.com"));

    @Test
    public void getDishNameTest() {
        assertEquals("rice", dishItem.getDishName());
    }

    @Test
    public void setDishNameTest() {
        dishItem.setDishName("chicken");
        assertEquals("chicken", dishItem.getDishName());
    }

    @Test
    public void getDishPriceTest() {
        assertEquals("5.00", dishItem.getDishPrice());
    }

    @Test
    public void setDishPriceTest() {
        dishItem.setDishPrice("10.10");
        assertEquals("10.10", dishItem.getDishPrice());
    }

    @Test
    public void getDishCategoryTest() {
        assertEquals("Main", dishItem.getDishCategory());
    }

    @Test
    public void setDishCategoryTest() {
        dishItem.setDishCategory("test");
        assertEquals("test", dishItem.getDishCategory());
    }

    @Test
    public void getDishImageUriTest() {
        assertEquals(Uri.parse("www.google.com"), dishItem.getDishImageUri());
    }

    @Test
    public void setDishImageUriTest() {
        dishItem.setDishImageUri(Uri.parse("www.youtube.com"));
        assertEquals(Uri.parse("www.youtube.com"), dishItem.getDishImageUri());
    }
}