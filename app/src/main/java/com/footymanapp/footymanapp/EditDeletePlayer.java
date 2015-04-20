package com.footymanapp.footymanapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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
 * Created by prend_000 on 10/04/2015.
 */
public class EditDeletePlayer extends ActionBarActivity {
    public static ArrayList<User> userList;
    private ArrayList<User> editList;
    private ProgressBar mProgressBar;
    static int result;
    public UserCustomAdapter theAdapter;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;
    public static User updateUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);
        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        userTable = mClient.getTable(User.class);
        userList = new ArrayList<>();
        editList = new ArrayList<>();
        theAdapter = new UserCustomAdapter(this, userList);
        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);
        getUser();
        final ListView userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter(theAdapter);

        Button editPlayer = (Button) findViewById(R.id.editButton);
        editPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).isSelected()) {
                        result = i;
                        updateUser = userList.get(i);
                        startActivity(new Intent(EditDeletePlayer.this, EditPlayerDetails.class));
                    }
                }
            }
        });
    Button deletePlayer = (Button) findViewById(R.id.deleteButton);
    deletePlayer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        for(int i = 0;i<userList.size();i++) {
            if (userList.get(i).isSelected()){
                checkItem(userList.remove(i));
                //refreshItemsFromTable();
                Log.i("CheckBox Test", userList.get(i).getFirstname());
            }
        }
    }
});

}
    public static int getResult() {
        return result;
    }

    public void getUser()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.where().field("ismanager").eq(false).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            theAdapter.clear();
                            for (User item : result) {
                                theAdapter.add(item);
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

    public void checkItem(final User item) {
        if (mClient == null) {
            return;
        }

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    userTable.delete(item);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(item.isSelected()) {
                                theAdapter.remove(item);
                            }
                            //refreshItemsFromTable();
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    public void setUserDetails()
    {

    }
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.where().field("selected").eq(false).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            theAdapter.clear();

                            for (User item : result) {
                                theAdapter.add(item);
                            }
                        }
                    });
                } catch (Exception exception) {
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
}
