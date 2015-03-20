package com.footymanapp.footymanapp;




public class User {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("password")
    private String password;

    private String DOB;
    private String position;
    private boolean isManger;
    private String phoneNumber;
    private String medicalCondition;
    private String email;
    private String teamid;

    public User(String id, String password){
        this.id = id;
        this.password = password;
    }

    public User (String id, String password,String DOB, String position, boolean isManager, String phoneNumber, String medicalCondition, String email, String teamid ){
        this.id = id;
        this.password = password;
        this.DOB= DOB;
        this.position = position;
        this.isManger = isManager;
        this.phoneNumber = phoneNumber;
        this.medicalCondition = medicalCondition;
        this.email= email;
        this.teamid = teamid;
    }

    public String getPassword(){
        return password;
    }
}
