package com.footymanapp.footymanapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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


public class Login extends ActionBarActivity {

    TextView username;
    TextView password;
    private Context t;
    private ProgressBar mProgressBar;
    private static MobileServiceClient mClient;
    protected static MobileServiceTable<User> userTable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);
        TextView registerButton = (TextView) findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, RegisterTeam.class));

            }
        });

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
            Log.i("tag", "connection started ...woohoo");
            userTable = mClient.getTable(User.class);
        } catch (MalformedURLException e) {
            Log.i("tag", "error with mobile service connection");
            e.printStackTrace();
        }
        //Context context = this;
        //DatabaseQueries.setupConnection(context);

        Button loginButton = (Button) findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                username = (TextView) findViewById(R.id.username);
                final String logusername = username.getText().toString();

                password = (TextView) findViewById(R.id.password);
                final String logpassword = password.getText().toString();

                new AsyncTask<Void, Void, Void>() {
                    protected Void doInBackground(Void... params) {
                        try {
                            MobileServiceList<User> result = userTable.where().field("id").eq(logusername).execute().get();
                            if(result.size() < 1)
                            {
                                Handler handler = new Handler(Looper.getMainLooper());
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        displayToast();
                                    }
                                }, 50);
                            }
                            for (User item : result)
                            {
                                if (logusername.equals(item.getId()) && logpassword.equals(item.getPassword()) && item.isIsmanager() == false)
                                {
                                    Intent intent = new Intent(Login.this, UserHome.class);
                                    intent.putExtra("teamName", item.getTeamid() );
                                    finish();
                                    startActivity(intent);
                                    Log.i("LOGIN WORKING", item.getId() + item.getPassword());
                                }
                                else if(logusername.equals(item.getId()) && logpassword.equals(item.getPassword()) && item.isIsmanager() == true)
                                {
                                    Intent intent = new Intent(Login.this, AdminHome.class);
                                    intent.putExtra("teamName", item.getTeamid() );
                                    finish();
                                    startActivity(intent);
                                }
                            }
                        } catch (Exception exception) {
                            Log.i("TAG", "error - dam");
                            exception.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }

        });

    }
    public void displayToast()
    {
        username.setText("");
        password.setText("");
        Toast toast = Toast.makeText(this, "Invalid login details!", Toast.LENGTH_SHORT);
        toast.show();
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
