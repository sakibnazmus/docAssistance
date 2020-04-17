package com.example.docassistance;

public class UserModel {
    String email,password,name,age,key,userId;


    UserModel(){};

    UserModel(String email, String password){
        this.email = email;
        this.password = password;
    }
    UserModel(String name,String age,String key){
        this.name = name;
        this.age= age;
        this.key = key;
    }

    public UserModel(String name, String age, String key, String userId) {
        this.name = name;
        this.age = age;
        this.key = key;
        this.userId = userId;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
