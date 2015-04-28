package com.footymanapp.footymanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserHome extends ActionBarActivity
{
    String playerLoggedIn;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        playerLoggedIn = getIntent().getExtras().getString("playerName");
        final String teamid = getIntent().getExtras().getString("teamName");

        TextView teamNameField = (TextView) findViewById(R.id.teamNameField);
        teamNameField.setText(teamid);

        Button nextGamePitchLoc = (Button) findViewById(R.id.userNextGameButton);
        nextGamePitchLoc.setOnClickListener(new View.OnClickListener()
        {
        @Override
                    public void onClick(View v)
        {
            startActivity(new Intent(UserHome.this, UserNextGame.class));
                    }
                });


        Button manDetails = (Button) findViewById(R.id.manDetails);
        manDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, ManagerDetails.class));
            }
        });

        Button editProfile = (Button) findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, UserEditProfile.class);
                intent.putExtra("playerName", playerLoggedIn);
                startActivity(intent);
            }
        });

        Button newsFeed = (Button) findViewById(R.id.newsFeed);
        newsFeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, News.class);
                intent.putExtra("teamName", teamid);
                startActivity(intent);
            }
        });
    }
}
