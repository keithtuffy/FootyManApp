package com.footymanapp.footymanapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;

import android.widget.ProgressBar;
//comment
/**
 * Created by Keith on 27/04/2015.
 */
public class News extends ActionBarActivity {
    public static ArrayList<MessageToSend> newsList;
    private ProgressBar mProgressBar;
    public NewsAdaptor theAdapter;
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

        newsTable = mClient.getTable("messages",MessageToSend.class);
        newsList = new ArrayList<>();
        //theAdapter = new UserCustomAdapter(this, newsList);
        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);

        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);
        //getUser();
        newsListView.setAdapter(theAdapter);

    }

    public void getNews()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<MessageToSend> result = newsTable.where().field("id").eq(teamid).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            theAdapter.clear();
                            for (MessageToSend item : result) {
                                //theAdapter.add(item);
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
}
