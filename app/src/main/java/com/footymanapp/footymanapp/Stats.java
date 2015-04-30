package com.footymanapp.footymanapp;

/**
 * Created by Keith on 20/03/2015.
 */
public class Stats {

    private String id;
    private String date;
    private int goals;
    private boolean cleansheet;
    private boolean subspaid;
    private String playerid;

    public Stats(String date, Integer goals, boolean cleansheet, boolean subspaid, String playerid){
        this.date = date;
        this.goals = goals;
        this.cleansheet = cleansheet;
        this.subspaid = subspaid;
        this.playerid = playerid;
    }
}
