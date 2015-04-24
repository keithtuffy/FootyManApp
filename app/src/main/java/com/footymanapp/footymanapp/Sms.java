package com.footymanapp.footymanapp;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Account;
import com.twilio.sdk.resource.instance.Message;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Keith on 24/04/2015.
 */
public class Sms {

    public static final String ACCOUNT_SID = "AC720676df0a9a027db406303d0aea7a8d";
    public static final String AUTH_TOKEN = "cb4e81b0934292397b329de6c9e94f44";


    public Sms(){
    /* Find your sid and token at twilio.com/user/account */

        TwilioRestClient client;
        client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);

        Account account = client.getAccount();

        MessageFactory messageFactory = account.getMessageFactory();
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("To", "+353857176955")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("From", "+12015618104")); // Replace with a valid phone number for your account.
        params.add(new BasicNameValuePair("Body", "Where's Wallace?"));
        try {
            Message sms = messageFactory.create(params);
        } catch (TwilioRestException e) {
            e.printStackTrace();
        }

    }
}