package com.footymanapp.footymanapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by prend_000 on 22/04/2015.
 */
public class ViewPlayer extends ActionBarActivity
{
    private ArrayList<User> userList;
    int result;
    private User updateUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player);

        updateUser = ViewDeletePlayer.updateUser;
        result = ViewDeletePlayer.getResult();
        userList = ViewDeletePlayer.userList;

        ImageView img = (ImageView) findViewById(R.id.profilepic);
        try {
            URL url = new URL("\"https://footymanapp.blob.core.windows.net/profilepics/Deam.jpg\"");
            //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
            HttpGet httpRequest = null;

            httpRequest = new HttpGet(url.toURI());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = (HttpResponse) httpclient
                    .execute(httpRequest);

            HttpEntity entity = response.getEntity();
            BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
            InputStream input = b_entity.getContent();

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            img.setImageBitmap(bitmap);

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        TextView un = (TextView) findViewById(R.id.username);
        un.setText(updateUser.getId());

        TextView fn = (TextView) findViewById(R.id.firstname);
        fn.setText(userList.get(result).getFirstname());

        TextView ln = (TextView) findViewById(R.id.lastname);
        ln.setText(userList.get(result).getLastname());

        TextView ph = (TextView) findViewById(R.id.phone);
        ph.setText(userList.get(result).getPhonenumber());

        TextView date = (TextView) findViewById(R.id.DOB);
        date.setText(userList.get(result).getDob());

        TextView em = (TextView) findViewById(R.id.email);
        em.setText(userList.get(result).getEmail());

        TextView mc = (TextView) findViewById(R.id.medicalcondition);
        mc.setText(userList.get(result).getMedicalcondition());

        TextView pos = (TextView) findViewById(R.id.position);
        pos.setText(userList.get(result).getPosition());

    }
}
