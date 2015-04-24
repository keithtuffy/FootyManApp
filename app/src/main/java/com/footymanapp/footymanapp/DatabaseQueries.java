package com.footymanapp.footymanapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
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


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;


/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    private ProgressBar mProgressBar;
    private static MobileServiceClient mClient;
    protected static MobileServiceTable<User> userTable;
    private static MobileServiceTable<Team> teamTable;
    private static MobileServiceTable<NextGameData> nextGameTable;
    private static String storageConnectionString;
    //public static boolean[] confirm = new boolean[1];
    //private static Context t;
    public DatabaseQueries()
    {
        Log.i("database", "table worked");
    }

    public static void addUser(final User user, final Context t) throws MalformedURLException {
        mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
        userTable = mClient.getTable(User.class);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done;
                try {
                    userTable.insert(user).get();
                    done = "true";
                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }

            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("add user", " add success");
                } else {
                    Log.i("add user", " add failed");

                }
            }
        }.execute();

    }

    public static void addTeam(final Team team, Context t) throws MalformedURLException {
        mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
        Log.i("tag", "connection started ...woohoo");
        teamTable = mClient.getTable(Team.class);


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done = "";
                try {
                    if(teamTable == null){
                        Log.i("team", " table is null");
                    }
                    else if(team == null){
                        Log.i("team", "team is null");
                    }

                    teamTable.insert(team).get();
                    done = "true";
                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }

            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("add team", " add success");
                } else {
                    Log.i("add team", " add failed");
                }
            }
        }.execute();
    }

    public static void addNextGame(final NextGameData ngd, Context t) throws MalformedURLException {
        mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
        nextGameTable = mClient.getTable("NextGame",NextGameData.class);


        // add new game
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                MobileServiceList<NextGameData> result= null;
                String done = "";
                try {
                    result = nextGameTable.where().field("id").eq(ngd.getTeamid()).execute().get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                if(result.size() !=0) {
                    try {
                        nextGameTable.update(ngd).get();
                        done = "true";
                    } catch (Exception e) {
                        done = "false";
                        e.printStackTrace();
                    }
                }
                else{
                    try {
                        nextGameTable.insert(ngd).get();
                        done = "true";
                    } catch (Exception e) {
                        done = "false";
                        e.printStackTrace();
                    }
                }
                return done;
            }

            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("Next Game TAG ", "Game Added");
                } else {
                    Log.i("Next Game TAG ", "Game Failed");
                }
            }
        }.execute();
    }


    public static void setBlobString(){
        storageConnectionString ="DefaultEndpointsProtocol=http;" + "AccountName=footymanapp;" + "AccountKey=dh3Mh8Yz3ue1St4sx4QMv8tBb4nzb8OiemxfBkbvtx7EeDeTqBxTSHREcGkwhIIuJUvpmklZxV0jvFFD13I7QA==";

    }

    public static void setStorageConnecton(final String dir){
        // Define the connection-string with your values
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done;
                try {
                    storageConnectionString ="DefaultEndpointsProtocol=http;" + "AccountName=footymanapp;" + "AccountKey=dh3Mh8Yz3ue1St4sx4QMv8tBb4nzb8OiemxfBkbvtx7EeDeTqBxTSHREcGkwhIIuJUvpmklZxV0jvFFD13I7QA==";

                    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                    // Create the blob client.
                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                    // Get a reference to a container.
                    // The container name must be lower case
                    CloudBlobContainer container = blobClient.getContainerReference(dir);

                    // Create the container if it does not exist.
                    container.createIfNotExists();
                    done = "true";
                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }

            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("storage connection", "success");
                } else {
                    Log.i("storage connection", "failed");

                }
            }
        }.execute();


    }

    public static void addPic(final String filePath,final String imgName, final boolean fromCamera,final Context content, final String picType) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done;

                try {
                    // Retrieve storage account from connection-string.
                    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                    // Create the blob client.
                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                    // Retrieve reference to a previously created container.
                    CloudBlobContainer container = blobClient.getContainerReference(picType);

                    // Define the path to a local file.
                    Log.i("filepath", filePath);

                    // Create or overwrite the "myimage.jpg" blob with contents from a local file.
                    CloudBlockBlob blob = container.getBlockBlobReference(imgName);
                    if(!fromCamera){
                        Uri uri = Uri.parse(filePath);
                        ContentResolver cr = content.getContentResolver();
                       // File source = new File(filePath);
                        InputStream fs = cr.openInputStream(uri);
                        File source = new File(uri.getPath());
                        blob.upload(fs,fs.available());
                        done = "true";
                    }
                    else {
                        File source = new File(filePath);
                        blob.upload(new FileInputStream(filePath), source.length());
                        done = "true";
                    }


                } catch (Exception e) {
                    // Output the stack trace.
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }
            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("add pic", "success");
                } else {
                    Log.i("add pic", "failed");

                }
            }
        }.execute();
    }

    public static void downloadProfilePic(final String username) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done = "false";
                try{
                    // Retrieve storage account from connection-string.
                    CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                    // Create the blob client.
                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                    // Retrieve reference to a previously created container.
                    CloudBlobContainer container = blobClient.getContainerReference("profilepics");

                    // Loop through each blob item in the container.
                    for (ListBlobItem blobItem : container.listBlobs()) {
                    // If the item is a blob, not a virtual directory.
                        CloudBlob blob = (CloudBlob) blobItem;

                        Log.i("blob",blob.getName() );
                        Log.i("blob",username );
                        if(blob.getName().equals(username+".jpg")){

                            blob.download(new FileOutputStream("/storage/emulated/0/footyman/" + blob.getName()));
                            done ="true";
                        };
                }


                } catch (Exception e) {
                    // Output the stack trace.
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }
            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i( "download pic", "success");
                } else {
                    Log.i("download pic", "failed");

                }
            }
        }.execute();
    }


    public static void sendNewMessage(final MessageToSend m, Context t) throws MalformedURLException {
        mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
        final MobileServiceTable<MessageToSend> messageTable = mClient.getTable("messages",MessageToSend.class);


        // add new game
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                MobileServiceList<NextGameData> result= null;
                String done = "";
                try {
                      messageTable.insert(m).get();
                      done = "true";
                    } catch (Exception e) {
                        done = "false";
                        e.printStackTrace();
                    }

                return done;
            }

            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("message sent", "Added");
                } else {
                    Log.i("message sent", "Failed");
                }
            }
        }.execute();
    }
}

