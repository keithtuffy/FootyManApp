package com.footymanapp.footymanapp;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.DatePicker;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by prend_000 on 07/04/2015.
 */
public class NextGameData {
    @com.google.gson.annotations.SerializedName("id")
    private String id;

    public NextGameData(String id) {
        this.id = id;
    }

    private String date;
    private String hometeam;
    private String awayteam;
    private String time;

    public NextGameData(String date, String hometeam, String awayteam, String time) {
        this.date = date;
        this.hometeam = hometeam;
        this.awayteam = awayteam;
        this.time = time;
    }

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

}
