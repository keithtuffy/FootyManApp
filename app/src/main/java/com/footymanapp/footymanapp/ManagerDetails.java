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
public class ManagerDetails extends ActionBarActivity
{
    ArrayList<User> managerDetails;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_details);
        managerDetails = new ArrayList<>();
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final MobileServiceList<User> result = DatabaseQueries.userTable.where().field("ismanager").eq(true).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            for (User item : result) {
                                managerDetails.add(item);

                                for (int i = 0; i < managerDetails.size(); i++) {
                                    TextView fname = (TextView) findViewById(R.id.manfname);
                                    fname.setText(managerDetails.get(i).getFirstname());

                                    TextView lname = (TextView) findViewById(R.id.manlname);
                                    lname.setText(managerDetails.get(i).getLastname());

                                    TextView phone = (TextView) findViewById(R.id.manphone);
                                    phone.setText(managerDetails.get(i).getPhonenumber());

                                    TextView email = (TextView) findViewById(R.id.manemail);
                                    email.setText(managerDetails.get(i).getEmail());
                                }
                            }
                        }
                    });
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
