package com.footymanapp.footymanapp;

import android.app.Activity;
import android.os.AsyncTask;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

import java.net.MalformedURLException;

/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    private MobileServiceClient mClient;
    private MobileServiceTable<User> userTable;

    public DatabaseQueries(MobileServiceClient mClient) {
        this.mClient = mClient;
        addUser();

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
