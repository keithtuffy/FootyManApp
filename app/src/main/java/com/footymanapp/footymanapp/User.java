package com.footymanapp.footymanapp;




public class User {

   // @com.google.gson.annotations.SerializedName("id")
    private String id;

   // @com.google.gson.annotations.SerializedName("password")
    private String password;
    private String firstname;
    public String lastname;
    private String dob;
    private String position;
    private boolean ismanager;
    private String phonenumber;
    private String medicalcondition;
    private String email;
    private String teamid;

    public User(String id, String password){
        this.id = id;
        this.password = password;
    }

    public User (String id,String firstname, String lastname, String password,String DOB,String medicalCondition, boolean isManager, String phoneNumber, String email, String position, String teamid ){
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.dob= DOB;
        this.position = position;
        this.ismanager = isManager;
        this.phonenumber = phoneNumber;
        this.medicalcondition = medicalCondition;
        this.email= email;
        this.teamid = teamid;
    }

    public String getPassword(){
        return password;
    }
    public String getLastname(){return lastname;}
}
