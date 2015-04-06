package com.footymanapp.footymanapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;


import java.net.MalformedURLException;
import java.util.concurrent.ExecutionException;

/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;
    private static MobileServiceTable<Team> teamTable;



    public DatabaseQueries() {

        Log.i("database", "table worked");
    }


   public static void setupConnection(Context t){
       try {
           mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
           Log.i("tag", "connection started ...woohoo");
           teamTable = mClient.getTable(Team.class);
           userTable = mClient.getTable(User.class);


       }
       catch (MalformedURLException e) {
           Log.i("tag","error with mobile service connection");
           e.printStackTrace();
       }
   }

    public static boolean login(final String username, final String password) throws ExecutionException, InterruptedException {
        final boolean[] confirm = new boolean[1];
        new AsyncTask<Void, Void, Void>() {
            boolean confirmDetails;
            protected Void doInBackground(Void... params) {
                try {
                    final User result = userTable.lookUp("keith").get();
                   if(result == null)
                       confirmDetails = false;
                    else if(result.getPassword() == password) {
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
    }


    public static void addUser(final User user){

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done;
                try {
                    userTable.insert(user).get();
                    done="true";
                } catch (Exception e) {
                    done="false";
                    e.printStackTrace();
                }
                return done;
            }
            protected void onPostExecute(String done){
                if(done.equals("true")){
                    Log.i("add user"," add sucess");
                }
                else{
                    Log.i("add user"," add failed");

                }
            }
        }.execute();

    }

    public static void addTeam(final Team team){


        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done="";
                try {
                    teamTable.insert(team).get();
                    done="true";
                } catch (Exception e) {
                    done="false";
                    e.printStackTrace();
                }
                return done;
            }
            protected void onPostExecute(String done){
                if(done.equals("true")){
                    Log.i("add team"," add sucess");
                }
                else{
                    Log.i("add team"," add failed");
                }
            }
        }.execute();
    }






}
