package com.footymanapp.footymanapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceList;

import java.util.ArrayList;

/**
 * Created by prend_000 on 15/04/2015.
 */
public class EditPlayerDetails extends ActionBarActivity
{
    private ArrayList<User> list;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);
    }
}
