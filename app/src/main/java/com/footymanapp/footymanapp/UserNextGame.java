package com.footymanapp.footymanapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by prend_000 on 08/04/2015.
 */
public class UserNextGame extends ActionBarActivity {
    private ArrayList<NextGameData> nextGameData;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<NextGameData> nextGameTable;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_next_game);
        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        nextGameTable = mClient.getTable("NextGame", NextGameData.class);
        nextGameData = new ArrayList<>();
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {

                    final MobileServiceList<NextGameData> result = nextGameTable.execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {

                            for (NextGameData item : result) {
                                nextGameData.add(item);
                                Log.i("DATE", "Date is "+item.getDate());}
                                for(int i = 0;i < nextGameData.size();i++)
                                {
                                    TextView d = (TextView) findViewById(R.id.userTextViewDate);
                                    d.setText(nextGameData.get(i).getDate());

                                    TextView ht = (TextView) findViewById(R.id.userHomeTeamEditText);
                                    ht.setText(nextGameData.get(i).getHomeTeam());

                                    TextView at = (TextView) findViewById(R.id.userAwayTeamEditText);
                                    at.setText(nextGameData.get(i).getAwayTeam());

                                    TextView ko = (TextView) findViewById(R.id.userKickOffEditText);
                                    ko.setText(nextGameData.get(i).getTime());
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
}
