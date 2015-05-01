package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;


public class AdminHome extends ActionBarActivity {
    ImageView crest;
    String team;
    String subsDate;
    private static MobileServiceClient mClient;
    private MobileServiceTable<Stats> statsTable;
    private static MobileServiceTable<User> userTable;
    public ArrayList<Stats> statsList;
    public ArrayList<User> userList;
    public UserCustomAdapter theAdapter;
    private ProgressBar mProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        statsTable = mClient.getTable(Stats.class);
        userTable = mClient.getTable(User.class);
        final String teamId = getIntent().getExtras().getString("teamName");
        team = teamId;
        statsList = new ArrayList<>();
        userList = new ArrayList<>();
        theAdapter = new UserCustomAdapter(this, userList);

        downloadTeamPic(teamId);
        crest = (ImageView) findViewById(R.id.crest);
        TextView teamNameField = (TextView) findViewById(R.id.teamNameField);
        teamNameField.setText(teamId);


        Button addPlayer = (Button) findViewById(R.id.addPlayer);
        addPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent addPlayer = new Intent(AdminHome.this, RegisterPlayer.class);
                addPlayer.putExtra("ismanager", "false");
                // will get team nme from screen - to be done
                addPlayer.putExtra("teamname","Pats");
                startActivity(addPlayer);


            }
        });

        Button subs = (Button) findViewById(R.id.subs);
        subs.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                        Intent intent = new Intent(AdminHome.this, SubsPayment.class);
                        intent.putExtra("Date", subsDate);
                        startActivity(intent);
            }
        });

        Button nextGame = (Button) findViewById(R.id.nextGame);
        nextGame.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, UserNextGame.class);
                intent.putExtra("teamName", teamId);
                startActivity(intent);
            }
        });
        Button addDelete = (Button) findViewById(R.id.editDeleteButton);
        addDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, ViewDeletePlayer.class);
                intent.putExtra("teamName", teamId);
                startActivity(intent);
            }
        });

        Button addNextGame = (Button) findViewById(R.id.addNextGame);
        addNextGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, NextGame.class);
                intent.putExtra("teamName", teamId);
                startActivity(intent);
            }
        });

        Button sendMessege = (Button) findViewById(R.id.sendmessage);
        sendMessege.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminHome.this, MessageActivity.class);
                intent.putExtra("teamName", teamId);
                startActivity(intent);
            }
        });




    }


    public void downloadTeamPic(final String teamname) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done = "false";
                try{
                    // Retrieve storage account from connection-string.
                    CloudStorageAccount storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=http;" + "AccountName=footymanapp;" + "AccountKey=dh3Mh8Yz3ue1St4sx4QMv8tBb4nzb8OiemxfBkbvtx7EeDeTqBxTSHREcGkwhIIuJUvpmklZxV0jvFFD13I7QA==");


                    // Create the blob client.
                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                    // Retrieve reference to a previously created container.
                    CloudBlobContainer container = blobClient.getContainerReference("teampics");
                    // Loop through each blob item in the container.

                    for (ListBlobItem blobItem : container.listBlobs()) {
                        // If the item is a blob, not a virtual directory.
                        CloudBlob blob = (CloudBlob) blobItem;
                        Log.i("blob", blob.getName());
                        Log.i("blob",teamname );
                        if(blob.getName().equals(teamname+".jpg")){
                            blob.download(new FileOutputStream(Environment.getExternalStorageDirectory() + "/download/" + blob.getName())); // saved to downloads for access
                            done ="true";
                        }
                    }


                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }
            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    crest.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/download/" + team + ".jpg"));
                    Log.i( "download pic", "success");
                } else {
                    Log.i("download pic", "failed");

                }

            }
        }.execute();

    }
    public void getStats() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<Stats> result = statsTable.where().field("date").eq(subsDate).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Stats item : result) {
                                statsList.add(item);
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    public void getUser() {
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
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            logoutAlert();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void logoutAlert()
    {
        AlertDialog.Builder logout = new AlertDialog.Builder(this);
        logout.setMessage("Are you sure you want to Log Out?");
        logout.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AdminHome.this,Login.class));
                finish();
                dialog.dismiss();

            }

        }).create();
        logout.setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }

        }).create();
        logout.show();
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
