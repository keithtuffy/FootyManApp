package com.footymanapp.footymanapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by prend_000 on 10/04/2015.
 */
public class EditDeletePlayer extends ActionBarActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        Button editPlayer = (Button) findViewById(R.id.editButton);
        editPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(EditDeletePlayer.this, RegisterPlayer.class));
            }
        });

        Button deletePlayer = (Button) findViewById(R.id.deleteButton);
        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
