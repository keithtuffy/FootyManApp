package com.footymanapp.footymanapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.notifications.NotificationsManager;

import java.net.MalformedURLException;


/**
 * Created by Keith on 22/04/2015.
 */
public class MessageActivity extends ActionBarActivity {
    Context t = this;
    public static MobileServiceClient mClient;

    public static final String SENDER_ID = "876142638198";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        try {
            mClient  = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", t);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        NotificationsManager.handleNotifications(this, SENDER_ID, MessageHandler.class);


        Button sendMessage = (Button) findViewById(R.id.sendmessage);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText message = (EditText) findViewById(R.id.message);
                String msg =  message.getText().toString();
                MessageToSend m = new MessageToSend(msg);
                try {
                    DatabaseQueries.sendNewMessage(m, MessageActivity.this);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }


            finish();
            }
        });

    }
}