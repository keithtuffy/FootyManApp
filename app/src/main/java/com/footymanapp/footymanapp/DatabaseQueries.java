package com.footymanapp.footymanapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    private MobileServiceClient mClient;
    private MobileServiceTable<User> userTable;
    private ArrayList<User> users;



    public DatabaseQueries(MobileServiceClient mClient) {
        this.mClient = mClient;
        userTable = mClient.getTable(User.class);
        Log.i("database", "table worked");
    }

    public boolean login(final String username, final String password) throws ExecutionException, InterruptedException {
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


    public void addUser(){

       final User user = new User("keith", "123456");

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done="";
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






}
