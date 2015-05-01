package com.footymanapp.footymanapp;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


/**
 * Created by Keith on 22/04/2015.
 */
public class MessageActivity extends ActionBarActivity {
    Context t = this;
    public static MobileServiceClient mClient;
    private String teamid;
    public static final String SENDER_ID = "876142638198";
    private static MobileServiceTable<User> userTable;
    private ArrayList<String> numbers= new ArrayList<>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        try {
            mClient  = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        teamid = getIntent().getExtras().getString("teamName");
        getUser();
        Button sendMessage = (Button) findViewById(R.id.sendmessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText message = (EditText) findViewById(R.id.message);
                DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy @ HH:mm");
                Date date = new Date();
                String newDate = dateFormat.format(date);
                String msg =  message.getText().toString();
                MessageToSend m = new MessageToSend(msg, teamid, newDate);
                try {
                    DatabaseQueries.sendNewMessage(m, MessageActivity.this);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                SmsSender.SendMessage(msg, numbers); // send message to twilio
            finish();
            }
        });

    }


    public void getUser()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    userTable = mClient.getTable(User.class);
                    final MobileServiceList<User> result = userTable.where().field("ismanager").eq(false).and().field("teamid").eq(teamid).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (User item : result) {
                                numbers.add(item.getPhonenumber());
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_backbutton, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_back) {
            MessageActivity.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}