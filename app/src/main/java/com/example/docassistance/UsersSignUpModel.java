package com.example.docassistance;

public class UsersSignUpModel {

    String email;
    String password;
    String uid;
    String address,phoneNumber,username;

    UsersSignUpModel() {

    }

    public UsersSignUpModel(String email, String password, String uid, String address, String phoneNumber, String username) {
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
    }

    UsersSignUpModel(String email, String password, String uid){
        this.email = email;
        this.password = password;
        this.uid = uid;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
