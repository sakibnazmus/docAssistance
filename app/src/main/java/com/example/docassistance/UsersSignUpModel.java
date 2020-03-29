package com.example.docassistance;

public class UsersSignUpModel {

    String email;
    String password;
    String uid;
    String address,phoneNumber,username;
    String key;

    UsersSignUpModel() {

    }

    public UsersSignUpModel(String email, String password, String uid, String address, String phoneNumber, String username, String key) {
        this.email = email;
        this.password = password;
        this.uid = uid;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.username = username;
        this.key = key;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
