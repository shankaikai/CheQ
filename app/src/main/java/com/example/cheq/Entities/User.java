package com.example.cheq.Entities;

public class User {

    private String userPhone, userName, userEmail, userPassword, userType;

    // The data obtained from database during log in should be passed into this constructor
    public User(String phone, String name, String email, String password, String type){
        this.userPhone = phone;
        this.userEmail = email;
        this.userName = name;
        this.userType = type;
        this.userPassword = password;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getName() {
        return userName;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public void setName(String name) {
        this.userName = name;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

}
