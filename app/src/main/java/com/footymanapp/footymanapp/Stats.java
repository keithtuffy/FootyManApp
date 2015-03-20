package com.footymanapp.footymanapp;

/**
 * Created by Keith on 20/03/2015.
 */
public class Stats {

    private String id;
    private String date;
    private int goals;
    private int cleansheets;
    private boolean subspaid;
    private String playerid;

    public Stats(String id, String date, int goals, int cleansheets, boolean subspaid, String playerid){
        this.id = id;
        this.date = date;
        this.goals = goals;
        this.cleansheets = cleansheets;
        this.subspaid = subspaid;
        this.playerid = playerid;
    }
}
