package com.footymanapp.footymanapp;

/**
 * Created by Keith on 22/04/2015.
 */
public class MessageToSend {

    private String id;
    private String message;
    private String date;
    private String teamid;

    public MessageToSend(String message, String teamid){
        this.message = message;
        date = "10-10-10";
        this.teamid = teamid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeamid() {
        return teamid;
    }

    public void setTeamid(String teamid) {
        this.teamid = teamid;
    }
}
