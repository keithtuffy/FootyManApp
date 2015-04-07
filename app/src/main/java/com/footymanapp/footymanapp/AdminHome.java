package com.footymanapp.footymanapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class AdminHome extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        Button addPlayer = (Button) findViewById(R.id.addPlayer);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlayer = new Intent(AdminHome.this, RegisterPlayer.class);
                addPlayer.putExtra("ismanager", "false");
                // will get team nme from screen - to be done
                addPlayer.putExtra("teamname","Newbridge");
                startActivity(addPlayer);


            }
        });

        Button subs = (Button) findViewById(R.id.subs);
        subs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(AdminHome.this, SubsPayment.class));
            }
        });
    }


        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register_team, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
