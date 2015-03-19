package com.footymanapp.footymanapp;




public class User {

    //@com.google.gson.annotations.SerializedName("id")
    private int id;

    //@com.google.gson.annotations.SerializedName("username")
    private String username;

    //@com.google.gson.annotations.SerializedName("password")
    private String password;

    public User(String username, String password)
    {
        this.username = username;
        this.password = password;
    }
}
