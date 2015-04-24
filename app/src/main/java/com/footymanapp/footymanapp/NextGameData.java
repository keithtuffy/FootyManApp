package com.footymanapp.footymanapp;

/**
 * Created by prend_000 on 07/04/2015.
 */
public class NextGameData {
    @com.google.gson.annotations.SerializedName("id")

    private String id;
    private String date;
    private String hometeam;
    private String awayteam;
    private String time;


    public NextGameData(String teamid,String date, String hometeam, String awayteam, String time ) {
        this.id = teamid;
        this.date = date;
        this.hometeam = hometeam;
        this.awayteam = awayteam;
        this.time = time;
    }
// use for update game
//    public NextGameData(String id,String date, String hometeam, String awayteam, String time, String teamid) {
//        this.id = id;
//        this.teamid = teamid;
//        this.date = date;
//        this.hometeam = hometeam;
//        this.awayteam = awayteam;
//        this.time = time;
//    }

    public String getHomeTeam() {
        return hometeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.hometeam = homeTeam;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAwayTeam() {
        return awayteam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayteam = awayTeam;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTeamid() {
        return id;
    }

    public void setTeamid(String teamid) {
        this.id = teamid;
    }

}
