package com.footymanapp.footymanapp;

import android.os.AsyncTask;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries {

    private MobileServiceClient mClient;
    private MobileServiceTable<User> userTable;



   /* new AsyncTask<Void, Void, Void>() {
        @Override
        protected Void doInBackground(Void... params) {
            try {
                final MobileServiceList<User> result = userTable.where().field("username").eq("footyman").execute().get();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.clear();

                        for (User item : result) {
                            mAdapter.add(item);
                        }
                    }
                });
            } catch (Exception exception) {
                createAndShowDialog(exception, "Error");
            }
            return null;
        }
    }.execute();
*/
    public DatabaseQueries()
    {

    }
}
