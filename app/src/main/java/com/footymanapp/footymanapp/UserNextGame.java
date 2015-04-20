package com.footymanapp.footymanapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserNextGame extends ActionBarActivity {
    private ArrayList<NextGameData> nextGameData;
    public ArrayList<Team> pitchData;
    private ProgressBar mProgressBar;
    Context t = this;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<NextGameData> nextGameTable;
    private static MobileServiceTable<Team> teamTable;
    private String homeTeam;
    String latitude;
    String longitude;
    String teamid;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_next_game);
        teamid = getIntent().getExtras().getString("teamName");
        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);
        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        nextGameTable = mClient.getTable("NextGame", NextGameData.class);
        teamTable = mClient.getTable(Team.class);
        nextGameData = new ArrayList<>();
        pitchData = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final MobileServiceList<NextGameData> result = nextGameTable.where().field("teamid").eq(teamid).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            for (NextGameData item : result) {
                                nextGameData.add(item);
                            }
                            for (int i = 0; i < nextGameData.size(); i++) {
                                TextView d = (TextView) findViewById(R.id.userTextViewDate);
                                d.setText(nextGameData.get(i).getDate());

                                TextView ht = (TextView) findViewById(R.id.userHomeTeamEditText);
                                ht.setText(nextGameData.get(i).getHomeTeam());
                                homeTeam = nextGameData.get(i).getHomeTeam();

                                TextView at = (TextView) findViewById(R.id.userAwayTeamEditText);
                                at.setText(nextGameData.get(i).getAwayTeam());

                                TextView ko = (TextView) findViewById(R.id.userKickOffEditText);
                                ko.setText(nextGameData.get(i).getTime());
                            }
                        }

                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<Team> result = teamTable.execute().get();
//                    final MobileServiceList<Team> result = teamTable.select("latitude", "longitude").and()
//                            .field("hometeam").eq(homeTeam).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (Team item : result) {
                                if (item.getId().equals(homeTeam))
                                {
                                    pitchData.add(item);
                                    latitude = item.getLatitude();
                                    longitude = item.getLongitude();
                                    Log.i("TEST", "Test" + item.getLatitude() + item.getLongitude());
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();

        Button pitchLocation = (Button) findViewById(R.id.userNextGamePitchLocation);
        pitchLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("TEST", "Test" + latitude + longitude);
                String label = "Game is here!";
                String uriBegin = "geo:" + latitude + "," + longitude;
                String query = latitude + "," + longitude;
                String encodedQuery = Uri.encode(query);
                String uriString = uriBegin + "?q=" + encodedQuery + "&z=18";
                Uri uri = Uri.parse(uriString);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                t.startActivity(intent);
//                String uri = String.format(Locale.ENGLISH, "geo:%s,%s ?q= ?z=%d",latitude,longitude,14);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                t.startActivity(intent);
            }
        });

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
