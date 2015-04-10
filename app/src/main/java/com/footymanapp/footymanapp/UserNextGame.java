package com.footymanapp.footymanapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserNextGame extends ActionBarActivity {
    private ArrayList<NextGameData> ngdList;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_next_game);

        ngdList = new ArrayList<NextGameData>(DatabaseQueries.getNextGame());

//        Log.i("DATE", "Date is "+ngdList.get(0).getDate());
//        Log.i("HOME", "Home is "+ngdList.get(0).getHomeTeam());
//        Log.i("AWAY", "Away is "+ngdList.get(0).getAwayTeam());
//        Log.i("TIME", "Time is "+ngdList.get(0).getTime());

        for(int i = 0;i < ngdList.size();i++)
        {
            TextView d = (TextView) findViewById(R.id.userTextViewDate);
            d.setText(ngdList.get(i).getDate());
            //String date = d.getText().toString();

            TextView ht = (TextView) findViewById(R.id.userHomeTeamEditText);
            ht.setText(ngdList.get(i).getHomeTeam());
            //String homeTeam = ht.getText().toString();

            TextView at = (TextView) findViewById(R.id.userAwayTeamEditText);
            at.setText(ngdList.get(i).getAwayTeam());
            //String awayTeam = at.getText().toString();

            TextView ko = (TextView) findViewById(R.id.userKickOffEditText);
            ko.setText(ngdList.get(i).getTime());
            //String kickOff = ko.getText().toString();
        }
    }
}
