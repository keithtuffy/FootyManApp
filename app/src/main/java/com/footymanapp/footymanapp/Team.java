package com.footymanapp.footymanapp;

/**
 * Created by Keith on 20/03/2015.
 */
public class Team {

    private String id;
    private String email;
    private String phonenumber;
    private String latitude;
    private String longitude;
    private String managername;
    private String ageGroup;

    public Team(String id, String email, String phonenumber, String managername, String ageGroup, String latitude, String longitude) {
        this.id=id;
        this.email = email;
        this.phonenumber = phonenumber;
        this.managername = managername;
        this.ageGroup = ageGroup;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}


