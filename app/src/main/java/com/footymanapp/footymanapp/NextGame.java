package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by prend_000 on 07/04/2015.
 */
public class NextGame extends ActionBarActivity

{
    TextView dateText;
    int mYear, mMonth, mDay;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_game);
        Button nextGamePitchLoc = (Button) findViewById(R.id.addNextGame);
        nextGamePitchLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView dateText = (TextView) findViewById(R.id.editTextDate);

                EditText date = (EditText) findViewById(R.id.editTextDate);
                String d = dateText.getText().toString();

                EditText homeTeam = (EditText) findViewById(R.id.homeTeamEditText);
                String ht = homeTeam.getText().toString();

                EditText awayTeam = (EditText) findViewById(R.id.awayTeamEditText);
                String at = awayTeam.getText().toString();

                TextView koTime = (TextView) findViewById(R.id.kickOffEditText);
                String kot = koTime.getText().toString();

                NextGameData ngd = new NextGameData(d, ht, at, kot);
                //DatabaseQueries.addNextGame(ngd);
                nextGameAddedAlert();

                dateText.setText("");
                homeTeam.setText("");
                awayTeam.setText("");
                koTime.setText("");
            }
        });

        final TextView dateEdit = (TextView) findViewById(R.id.editTextDate);
            dateEdit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    //To show current date in the datepicker
                    Calendar mcurrentDate = Calendar.getInstance();
                    int mYear = mcurrentDate.get(Calendar.YEAR);
                    int mMonth = mcurrentDate.get(Calendar.MONTH);
                    int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog mDatePicker;
                    mDatePicker = new DatePickerDialog(NextGame.this, new DatePickerDialog.OnDateSetListener() {
                        public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                            // TODO Auto-generated method stub
                    /*      Your code   to get date and time    */
                            selectedmonth = selectedmonth + 1;
                            dateEdit.setText("" + selectedday + "-" + selectedmonth + "-" + selectedyear);
                        }
                    }, mYear, mMonth, mDay);
                    mDatePicker.setTitle("Select Date");
                    mDatePicker.show();
                }
            });

        final TextView timeEdit = (TextView) findViewById(R.id.kickOffEditText);
        timeEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(NextGame.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timeEdit.setText(String.format("%02d", selectedHour) + ":" + String.format("%02d", selectedMinute));
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });
        }
    public void nextGameAddedAlert()
    {
        AlertDialog.Builder playerAlert = new AlertDialog.Builder(this);
        playerAlert.setMessage("Your next game has been saved.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                startActivity(new Intent(NextGame.this, AdminHome.class));
                finish();

            }
        }).create();
        playerAlert.show();
    }
}



