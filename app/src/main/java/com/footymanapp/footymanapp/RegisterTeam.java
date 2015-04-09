package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class RegisterTeam extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);

        // when the register team button is pressed
        Button regTeam = (Button) findViewById(R.id.registerTeam);
        regTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                TextView tn = (TextView) findViewById(R.id.firstname);
                String teamname = tn.getText().toString();

                TextView em = (TextView) findViewById(R.id.email);
                String email = em.getText().toString();

                TextView ph = (TextView) findViewById(R.id.phone);
                String phone = ph.getText().toString();

                TextView mn = (TextView) findViewById(R.id.managerName);
                String managername = mn.getText().toString();

                TextView ag = (TextView) findViewById(R.id.agegroup);
                String agegroup = ag.getText().toString();


                String pitchLocation = String.valueOf(MapsActivity.getLatitude()) + ", " + String.valueOf(MapsActivity.getLongitude());
                //Log.i("tag69", "Pitch location is " + MapsActivity.getLatitude() + ", " + MapsActivity.getLongitude());

                //validtion
                if(teamname.length() == 0){
                    tn.setError("Please enter a team name");
                }
                else if(email.length() == 0|| !email.contains("@")){
                    em.setError("Please enter a valid email");
                    tn.setError(null);
                    ph.setError(null);
                    mn.setError(null);
                    ag.setError(null);
                }
                else if(phone.length() == 0){
                    ph.setError("Please enter a phone number");
                    tn.setError(null);
                    em.setError(null);
                    mn.setError(null);
                    ag.setError(null);
                }
                else if(managername.length() == 0){
                    mn.setError("Please enter a manager name");
                    tn.setError(null);
                    em.setError(null);
                    ph.setError(null);
                    ag.setError(null);
                }
                else if(agegroup.length() == 0){
                    ag.setError("Please enter an age group");
                    tn.setError(null);
                    em.setError(null);
                    ph.setError(null);
                    mn.setError(null);
                }
                else if(pitchLocation.length() == 0){
                    Toast toast = Toast.makeText(RegisterTeam.this, "Please set a location", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {

                    Team team = new Team(teamname, email, phone, pitchLocation, managername, agegroup);
                    Log.i("teamname", tn.getText().toString());
                    DatabaseQueries.addTeam(team);

                    tn.setError(null);
                    em.setError(null);
                    ph.setError(null);
                    mn.setError(null);
                    ag.setError(null);


                    tn.setText("");
                    em.setText("");
                    ph.setText("");
                    mn.setText("");
                    ag.setText("");
                    teamCreationAlert(teamname);
                }
            }
        });



        // used to search googlemap for pitch location
        TextView pitchLocation = (TextView) findViewById(R.id.pitchlocation);
        pitchLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                   startActivity(new Intent(RegisterTeam.this, MapsActivity.class));

            }
        }




    );}


    public void teamCreationAlert(final String teamname)
    {
        AlertDialog.Builder teamAlert = new AlertDialog.Builder(this);
        teamAlert.setMessage("\t\tCongratulations!!\nYour Team has been created. Press 'OK' to create your profile").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterTeam.this,RegisterPlayer.class);
                intent.putExtra("ismanager", "true");
                intent.putExtra("teamname",teamname);
                startActivity(intent);
                finish();
                dialog.dismiss();

            }
        }).create();
        teamAlert.show();
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

