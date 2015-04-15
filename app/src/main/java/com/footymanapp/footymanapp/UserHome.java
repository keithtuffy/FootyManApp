package com.footymanapp.footymanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserHome extends ActionBarActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
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
                startActivity(new Intent(UserHome.this, ManagerContact.class));
            }
        });
            }
}
