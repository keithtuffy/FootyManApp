package com.footymanapp.footymanapp;

public class User {

    @com.google.gson.annotations.SerializedName("id")
    private String id;

    @com.google.gson.annotations.SerializedName("password")
    private String password;
//    public User(String id, String password){
//        this.id = id;
//        this.password = password;
//    }

    private String firstname;
    private String lastname;
    private String dob;
    private String position;
    private boolean ismanager;
    private String phonenumber;
    private String medicalcondition;
    private String email;
    private String teamid;
    private boolean selected;

    public User (String id, String firstname, String lastname, String password, String DOB, String medicalCondition, boolean isManager, String phoneNumber, String email, String position, String teamid ){
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
        selected = false;
    }

    public String getPassword(){
        return password;
    }

    public void setLastname(String name){this.lastname = lastname;}

    public String getLastname(){return lastname;}

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isIsmanager() {
        return ismanager;
    }

    public void setIsmanager(boolean ismanager) {
        this.ismanager = ismanager;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getMedicalcondition() {
        return medicalcondition;
    }

    public void setMedicalcondition(String medicalcondition) {
        this.medicalcondition = medicalcondition;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
