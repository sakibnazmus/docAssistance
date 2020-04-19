package com.example.docassistance;

public class UserModel {
   String fullName,email,gender,dateOfBirth,userUID,address;

   UserModel(){

   }

    public UserModel(String fullName, String email, String gender, String dateOfBirth, String userUID) {
        this.fullName = fullName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.userUID = userUID;
    }

    public UserModel(String fullName, String email,String address, String userUID) {
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.userUID = userUID;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getUserUID() {
        return userUID;
    }

    public void setUserUID(String userUID) {
        this.userUID = userUID;
    }
}
