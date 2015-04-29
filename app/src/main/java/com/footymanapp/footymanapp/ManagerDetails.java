package com.footymanapp.footymanapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
 * Created by prend_000 on 15/04/2015.
 */
public class ManagerDetails extends ActionBarActivity
{
    private MobileServiceClient mClient;
    private MobileServiceTable<User> userTable;
    private ProgressBar mProgressBar;
    ArrayList<User> managerDetails;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_details);
        managerDetails = new ArrayList<>();

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        userTable = mClient.getTable(User.class);
        new AsyncTask<Void, Void, Void>()
        {
            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final MobileServiceList<User> result = userTable.where().field("ismanager").eq(true).execute().get();
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_backbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            ManagerDetails.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
