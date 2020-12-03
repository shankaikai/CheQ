package com.example.cheq.Entities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    //String phone, String name, String email, String password, String type
    User user = new User("88888888","Tom","tom.com","helloo", "User");

    @Test
    public void getUserPhoneTest() {
        Assert.assertEquals("88888888", user.getUserPhone());
    }


    @Test
    public void setUserPhoneTest() {
        user.setUserPhone("88888889");
        Assert.assertEquals("88888889", user.getUserPhone());
    }

    @Test
    public void getNameTest() {
        Assert.assertEquals("Tom", user.getName());
    }

    @Test
    public void setNameTest() {
        user.setName("Bob");
        Assert.assertEquals("Bob", user.getName());
    }

    @Test
    public void getUserEmailTest() {
        Assert.assertEquals("tom.com", user.getUserEmail());
    }

    @Test
    public void setUserEmailTest() {
        user.setUserEmail("bob.com");
        Assert.assertEquals("bob.com", user.getUserEmail());
    }

    @Test
    public void getUserPasswordTest() {
        Assert.assertEquals("helloo", user.getUserPassword());
    }

    @Test
    public void setUserPasswordTest() {
        user.setUserPassword("byebye");
        Assert.assertEquals("byebye", user.getUserPassword());
    }

    @Test
    public void getUserTypeTest() {
        Assert.assertEquals("User", user.getUserType());
    }

    @Test
    public void setUserTypeTest() {
        user.setUserType("Restaurant");
        Assert.assertEquals("Restaurant", user.getUserType());
    }
}