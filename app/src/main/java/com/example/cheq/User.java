package com.example.cheq;

public class User {

    private int id;
    private String phone;
    private String name;
    private String email;
    private String type; // Whether customer or restaurant owner

    // The data obtained from database during log in should be passed into this constructor
    public User(int id, String phone, String name, String email, String type){
        this.id = id;
        this.phone = phone;
        this.email = email;
        this.name = name;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
}
