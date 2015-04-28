
package com.footymanapp.footymanapp;


/**
 * Created by Keith on 16/04/2015.
 */

// Download the twilio-java library from http://twilio.com/docs/libraries

import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;

//import com.twilio.sdk.TwilioRestException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class SmsSender {


/* Find your sid and token at twilio.com/user/account */

    public static final String ACCOUNT_SID = "AC720676df0a9a027db406303d0aea7a8d";
    public static final String AUTH_TOKEN = "cb4e81b0934292397b329de6c9e94f44";

    public SmsSender() {}

    public static void SendMessage(final String msg){
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done;
                try {

                    HttpClient httpclient = new DefaultHttpClient();

                    HttpPost httppost = new HttpPost("https://api.twilio.com/2010-04-01/Accounts/AC720676df0a9a027db406303d0aea7a8d/SMS/Messages");
                    String base64EncodedCredentials = "Basic "+ Base64.encodeToString((ACCOUNT_SID + ":" + AUTH_TOKEN).getBytes(), Base64.NO_WRAP);

                    httppost.setHeader("Authorization",
                            base64EncodedCredentials);
                    try {

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                        nameValuePairs.add(new BasicNameValuePair("From",
                                "+12015618104"));
                        nameValuePairs.add(new BasicNameValuePair("To",
                                "+353857399798"));
                        nameValuePairs.add(new BasicNameValuePair("Body",
                                msg));

                        httppost.setEntity(new UrlEncodedFormEntity(
                                nameValuePairs));

                        // Execute HTTP Post Request
                        HttpResponse response = httpclient.execute(httppost);
                        HttpEntity entity = response.getEntity();
                        System.out.println("Entity post is: "
                                + EntityUtils.toString(entity));


                    } catch (ClientProtocolException e) {

                    } catch (IOException e) {

                    }

                    done = "true";
                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }

            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i("sms", "success");
                } else {
                    Log.i("sms", "failed");

                }
            }
        }.execute();

    }
}


