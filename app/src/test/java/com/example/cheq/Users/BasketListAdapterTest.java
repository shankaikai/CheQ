package com.example.cheq.Users;

import com.example.cheq.Login.RestaurantOnboard.DishItem;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class BasketListAdapterTest {

    BasketListAdapter basketListAdapter;

    {
        HashMap<String, HashMap<String, String>> info = new HashMap<>();
        HashMap<String, String> a = new HashMap<>();
        a.put("a", "b");
        info.put("1", a);
        basketListAdapter = new BasketListAdapter(info);
    }

    @Test
    public void getItemCountTest() {
        assertEquals(1, basketListAdapter.getItemCount());
    }
}