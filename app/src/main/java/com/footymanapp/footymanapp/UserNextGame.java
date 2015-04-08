package com.footymanapp.footymanapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserNextGame extends ActionBarActivity {
    private ArrayList<NextGameData> ngdList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_next_game);

        ngdList = new ArrayList<>(DatabaseQueries.getNextGame());

        Log.i("DATE", "Date is "+ngdList.get(0).getDate());
    }
}
