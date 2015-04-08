package com.footymanapp.footymanapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Keith on 26/02/2015.
 */
public class DatabaseQueries extends Activity {

    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;
    private static MobileServiceTable<Team> teamTable;
    private static MobileServiceTable<NextGameData> nextGameTable;
    private static ArrayList<NextGameData> nextGameData;
    private static ArrayList<User> lastNames;
    private CustomAdapter cAdapter;

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
            nextGameData = new ArrayList<>();
            lastNames = new ArrayList<>();
        } catch (MalformedURLException e) {
            Log.i("tag", "error with mobile service connection");
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
    }


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

        public void showAll()
        {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            cAdapter.clear();
                            for (User item : result) {
                                cAdapter.add(item);
                            }
                        }
                    });
                } catch (Exception exception)
                {
                 exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public static ArrayList<User> getUser()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.execute().get();
                    lastNames.clear();
                    for (User item : result) {
                        lastNames.add(item);
                        Log.i("UserTable", "First Name: " + item.getFirstname());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
        return lastNames;
    }

    public static ArrayList<NextGameData> getNextGame()
    {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final MobileServiceList<NextGameData> result = nextGameTable.execute().get();
                    for(NextGameData item : result)
                    {
                        nextGameData.add(item);
                        //Log.i("NextGameData", "Date is " + item.getDate());
                    }

                } catch (Exception exception)
                {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
        return nextGameData;
    }
    /*public static ArrayList<NextGameData> getNextGame() {
        try {
            nextGameTable.execute(new TableQueryCallback<NextGameData>() {
                public void onCompleted(List<NextGameData> result, int count,
                                        Exception exception, ServiceFilterResponse response) {
                    if (exception == null)//it found something in the db
                    {
                        nextGameData.clear();
                        for (NextGameData item : result) {
                            nextGameData.add(item);
                            Log.i("NextGameData", "Date is " + item.getDate());
                        }

                    } else {
                        exception.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nextGameData;
    }*/
}
