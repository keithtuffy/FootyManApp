package com.footymanapp.footymanapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;
//comment
/**
 * Created by Keith on 27/04/2015.
 */
public class News extends ActionBarActivity {
    public static ArrayList<MessageToSend> newsList;
    private ProgressBar mProgressBar;
    public NewsAdapter theAdapter;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<MessageToSend> newsTable;
    private String teamid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        newsTable = mClient.getTable("Messages", MessageToSend.class);
        newsList = new ArrayList<>();
        theAdapter = new NewsAdapter(this, newsList);
        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        teamid = getIntent().getExtras().getString("teamName");
        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);
        getNews();
        ListView newsListView = (ListView) findViewById(android.R.id.list);
        newsListView.setAdapter(theAdapter);

    }

    public void getNews()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<MessageToSend> result = newsTable.where().field("teamid").eq(teamid).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run()
                        {
                            theAdapter.clear();
                            for (MessageToSend item : result)
                            {
                                theAdapter.add(item);
                            }
                        }
                    });
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
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
