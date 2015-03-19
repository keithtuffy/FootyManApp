package com.footymanapp.footymanapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.net.MalformedURLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    protected static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;

    public void startConnection()
    {
        try {
            if(mClient !=null) {
                DatabaseQueries.mClient = new MobileServiceClient("https://footyman.azure-mobile.net/", "IcbgNlIXFduHJugOgGwkqmufBMfPaN69", this);
                Log.i("tag", "connection started ...woohoo");
            }
        } catch (MalformedURLException e) {
            Log.i("tag","error with mobile service connection");
            e.printStackTrace();
        }


    }

    public boolean login(String username, String password) throws ExecutionException, InterruptedException {

        userTable = mClient.getTable(User.class);



        userTable.where().field("username").eq(username)
                .and().field("password").eq(password)
                .execute().get();
        if(userTable == null)
        {
            return false;
        }

        return true;
    }


    public void addUser(){

        User user = new User("pat", "12345678");
        mClient.getTable(User.class).insert(user, new TableOperationCallback<User>() {
            public void onCompleted(User entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded..
                } else {
                    // Insert failed
                }
            }
        });

    }






}
