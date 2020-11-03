package com.example.testapp;

public class User {
    private int id;
    private String phone;

    public User(int id, String phone){
        this.id = id;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }
}
