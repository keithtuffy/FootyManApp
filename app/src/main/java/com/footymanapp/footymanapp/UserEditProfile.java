package com.footymanapp.footymanapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by prend_000 on 20/04/2015.
 */
public class UserEditProfile extends ActionBarActivity {


    private static ArrayList<User> userList;
    private int result = 0;
    private String playerLoggedIn;
    private User updateUser;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;
    private ProgressBar mProgressBar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        playerLoggedIn = getIntent().getExtras().getString("playerName");
        updateUser = EditDeletePlayer.updateUser;
        result = EditDeletePlayer.getResult();
        userList = EditDeletePlayer.userList;

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
            Log.i("tag", "connection started ...woohoo");
            userTable = mClient.getTable(User.class);
        } catch (MalformedURLException e) {
            Log.i("tag", "error with mobile service connection");
            e.printStackTrace();
        }
        userTable = mClient.getTable(User.class);
        userList = new ArrayList<>();
        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            //userList.clear();
                            for (User item : result)
                            {
                                if(item.getId().equals(playerLoggedIn))
                                userList.add(item);

                                for(int i = 0;i<userList.size();i++) {
                                    TextView un = (TextView) findViewById(R.id.username);
                                    un.setText(userList.get(i).getId());

                                    TextView fn = (TextView) findViewById(R.id.firstname);
                                    fn.setText(userList.get(i).getFirstname());

                                    TextView ln = (TextView) findViewById(R.id.lastname);
                                    ln.setText(userList.get(i).getLastname());

                                    TextView ph = (TextView) findViewById(R.id.phone);
                                    ph.setText(userList.get(i).getPhonenumber());

                                    TextView date = (TextView) findViewById(R.id.DOB);
                                    date.setText(userList.get(i).getDob());

                                    TextView em = (TextView) findViewById(R.id.email);
                                    em.setText(userList.get(i).getEmail());

                                    TextView mc = (TextView) findViewById(R.id.medicalcondition);
                                    mc.setText(userList.get(i).getMedicalcondition());

                                    TextView pos = (TextView) findViewById(R.id.position);
                                    pos.setText(userList.get(i).getPosition());

                                    TextView pw = (TextView) findViewById(R.id.password);
                                    pw.setText(userList.get(i).getPassword());
                                }
                            }
                        }
                    });
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

    }



    private class ProgressFilter implements ServiceFilter {

        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(
                ServiceFilterRequest request, NextServiceFilterCallback next) {

            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            SettableFuture<ServiceFilterResponse> result = SettableFuture.create();
            try {
                ServiceFilterResponse response = next.onNext(request).get();
                result.set(response);
            } catch (Exception exc) {
                result.setException(exc);
            }

            dismissProgressBar();
            return result;
        }

        private void dismissProgressBar() {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                }
            });
        }
    }
}