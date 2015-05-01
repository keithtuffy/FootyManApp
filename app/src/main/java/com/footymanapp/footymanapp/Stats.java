package com.footymanapp.footymanapp;

/**
 * Created by Keith on 20/03/2015.
 */
public class Stats {

    private String id;
    private String firstname;
    private String lastname;
    private String date;
    private boolean subspaid;
    private String playerid;

    public Stats(String firstname, String lastname, String date, boolean subspaid, String playerid){
        this.firstname = firstname;
        this.lastname = lastname;
        this.date = date;
        this.subspaid = subspaid;
        this.playerid = playerid;
    }

    public String getId() {
        return id;
    }
    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isSubspaid() {
        return subspaid;
    }

    public void setSubspaid(boolean subspaid) {
        this.subspaid = subspaid;
    }

    public String getPlayerid() {
        return playerid;
    }

    public void setPlayerid(String playerid) {
        this.playerid = playerid;
    }
}
