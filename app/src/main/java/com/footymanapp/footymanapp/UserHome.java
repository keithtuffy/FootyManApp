package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.io.FileOutputStream;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserHome extends ActionBarActivity
{
    private String playerLoggedIn;
    private ImageView crest;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        playerLoggedIn = getIntent().getExtras().getString("playerName");
        final String teamid = getIntent().getExtras().getString("teamName");
        downloadTeamPic(teamid);
        crest = (ImageView) findViewById(R.id.crest);


        TextView teamNameField = (TextView) findViewById(R.id.teamNameField);
        teamNameField.setText(teamid);

        Button nextGamePitchLoc = (Button) findViewById(R.id.userNextGameButton);
        nextGamePitchLoc.setOnClickListener(new View.OnClickListener()
        {
        @Override
        public void onClick(View v)
        {
            Intent intent = new Intent(UserHome.this, UserNextGame.class);
            intent.putExtra("teamName", teamid);
            startActivity(intent);
                    }
                });


        Button manDetails = (Button) findViewById(R.id.manDetails);
        manDetails.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(UserHome.this, ManagerDetails.class));
            }
        });

        Button editProfile = (Button) findViewById(R.id.editProfile);
        editProfile.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, UserEditProfile.class);
                intent.putExtra("playerName", playerLoggedIn);
                startActivity(intent);
            }
        });

        Button newsFeed = (Button) findViewById(R.id.newsFeed);
        newsFeed.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(UserHome.this, News.class);
                intent.putExtra("teamName", teamid);
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
                    crest.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/download/" + teamname + ".jpg"));
                    Log.i( "download pic", "success");
                } else {
                    Log.i("download pic", "failed");

                }

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
                startActivity(new Intent(UserHome.this,Login.class));
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
}
