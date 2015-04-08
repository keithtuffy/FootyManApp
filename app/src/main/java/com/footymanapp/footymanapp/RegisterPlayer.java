package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Keith on 01/04/2015.
 */
public class RegisterPlayer extends ActionBarActivity {

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);



        Button regPlayer = (Button) findViewById(R.id.registerPlayer);
        regPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView un = (TextView) findViewById(R.id.username);
                String username = un.getText().toString();

                TextView fn = (TextView) findViewById(R.id.firstname);
                String firstname = fn.getText().toString();

                TextView ln = (TextView) findViewById(R.id.lastname);
                String lastname = ln.getText().toString();

                TextView ph = (TextView) findViewById(R.id.phone);
                String phone = ph.getText().toString();

                TextView date = (TextView) findViewById(R.id.DOB);
                String DOB = date.getText().toString();

                TextView em = (TextView) findViewById(R.id.email);
                String email = em.getText().toString();

                TextView mc = (TextView) findViewById(R.id.medicalcondition);
                String medicalcondition = mc.getText().toString();

                TextView pos = (TextView) findViewById(R.id.position);
                String position = pos.getText().toString();

                TextView pw = (TextView) findViewById(R.id.password);
                String password = pw.getText().toString();


                final boolean ismanager = Boolean.valueOf(getIntent().getExtras().getString("ismanager"));
                final String teamname =  getIntent().getExtras().getString("teamname");


                User user = new User(username, firstname, lastname, password, DOB, medicalcondition, ismanager, phone, email, position, teamname );
                DatabaseQueries.addUser(user);
                playerCreationAlert();

                un.setText("");
                fn.setText("");
                ln.setText("");
                ph.setText("");
                date.setText("");
                em.setText("");
                mc.setText("");
                pos.setText("");
                pw.setText("");
            }
        });
    }

    public void playerCreationAlert()
    {
        AlertDialog.Builder playerAlert = new AlertDialog.Builder(this);
        playerAlert.setMessage("\tCongratulations,\nYour Profile has been created").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent addPlayer = new Intent(RegisterPlayer.this, AdminHome.class);
                addPlayer.putExtra("ismanager", "false");
                addPlayer.putExtra("teamname","Newbridge");
                startActivity(addPlayer);

                finish();

            }
        }).create();
        playerAlert.show();
    }
}
