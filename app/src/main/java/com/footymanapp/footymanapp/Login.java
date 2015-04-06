package com.footymanapp.footymanapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;


public class Login extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView username = (TextView) findViewById(R.id.username);

        final String logusername = username.getText().toString();

        TextView password = (TextView) findViewById(R.id.lastname);

        final String logpassword = password.getText().toString();

    TextView registerButton = (TextView) findViewById(R.id.registerButton);
    registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterTeam.class));
          }
        });




        Context context = this;
        DatabaseQueries.setupConnection(context);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener(){

       @Override
       public void onClick(View v) {
           startActivity(new Intent(Login.this, AdminHome.class));
           try {
               if (DatabaseQueries.login(logusername, logpassword)) {
                   startActivity(new Intent(Login.this, AdminHome.class));
               }
           } catch (InterruptedException e) {
               e.printStackTrace();
           } catch (ExecutionException e) {
               e.printStackTrace();
           }


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
