package com.footymanapp.footymanapp;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;


import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.blob.*;


/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;
    private static MobileServiceTable<Team> teamTable;
    private static MobileServiceTable<NextGameData> nextGameTable;
    private static String storageConnectionString;

    public DatabaseQueries() {
        Log.i("database", "table worked");
    }


    public static void setupConnection(Context t) {
        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
            Log.i("tag", "connection started ...woohoo");
            teamTable = mClient.getTable(Team.class);
            userTable = mClient.getTable(User.class);
            nextGameTable = mClient.getTable("NextGame", NextGameData.class);
        } catch (MalformedURLException e) {
            Log.i("tag", "error with mobile service connection");
            e.printStackTrace();
        }
    }
    /*public static boolean login(final String username, final String password) throws ExecutionException, InterruptedException {
        final boolean[] confirm = new boolean[1];
        new AsyncTask<Void, Void, Void>() {
            boolean confirmDetails;

            protected Void doInBackground(Void... params) {
                try {
                    final User result = userTable.lookUp("keith").get();
                    if (result == null)
                        confirmDetails = false;
                    else if (result.getPassword() == password) {
                        confirmDetails = true;
                        Log.i("TAG", "results work.....little daisy");
                    }

                } catch (Exception exception) {
                    Log.i("TAG", "error - dam");
                    exception.printStackTrace();
                    confirmDetails = false;
                }
                confirm[0] = confirmDetails;
                return null;
            }
        }.execute();


        return confirm[0];
    }*/


    public static void addUser(final User user) {

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

    public static void addTeam(final Team team) {


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done = "";
                try {
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

    public static void addNextGame(final NextGameData ngd) {


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done = "";
                try {
                    nextGameTable.insert(ngd).get();
                    done = "true";
                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
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

    public static void setStorageConnecton(){
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
                    CloudBlobContainer container = blobClient.getContainerReference("profilepics");

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

    public static void addProfilePic(Uri path, String imgName){
        try
        {
            // Retrieve storage account from connection-string.
            CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

            // Create the blob client.
            CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

            // Retrieve reference to a previously created container.
            CloudBlobContainer container = blobClient.getContainerReference("profilepics");

            // Define the path to a local file.
            final String filePath = path.toString();

            // Create or overwrite the "myimage.jpg" blob with contents from a local file.
            CloudBlockBlob blob = container.getBlockBlobReference(imgName);
            File source = new File(filePath);
            blob.upload(new FileInputStream(source), source.length());
        }
        catch (Exception e)
        {
            // Output the stack trace.
            e.printStackTrace();
        }
    }
}
