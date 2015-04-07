package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by prend_000 on 07/04/2015.
 */
public class NextGame extends ActionBarActivity

    {

        protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_game);

        Button nextGamePitchLoc = (Button) findViewById(R.id.addNextGame);
            nextGamePitchLoc.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    EditText date = (EditText) findViewById(R.id.editTextDate);
                    String d = date.getText().toString();

                    EditText homeTeam = (EditText) findViewById(R.id.homeTeamEditText);
                    String ht = homeTeam.getText().toString();

                    EditText awayTeam = (EditText) findViewById(R.id.awayTeamEditText);
                    String at = awayTeam.getText().toString();

                    EditText koTime = (EditText) findViewById(R.id.kickOffEditText);
                    String kot = koTime.getText().toString();

                    NextGameData ngd = new NextGameData(d, ht, at, kot);
                    DatabaseQueries.addNextGame(ngd);
                    nextGameAddedAlert();

                    date.setText("");
                    homeTeam.setText("");
                    awayTeam.setText("");
                    koTime.setText("");
                }
            });
        }
        public void nextGameAddedAlert()
        {
            AlertDialog.Builder nextGameAlert = new AlertDialog.Builder(this);
            nextGameAlert.setMessage("Your next game details have been saved").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    dialog.dismiss();

                }
            }).create();
            nextGameAlert.show();
        }
}
