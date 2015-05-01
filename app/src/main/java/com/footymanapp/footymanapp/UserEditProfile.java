package com.footymanapp.footymanapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by prend_000 on 20/04/2015.
 */
public class UserEditProfile extends ActionBarActivity {


    private static ArrayList<User> userList;
    private int result = 0;
    private String playerLoggedIn;
    private User updateUser;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;
    private ProgressBar mProgressBar;
    private ImageView img;

    public TextView getUn() {
        return un;
    }

    public TextView un;
    private TextView fn;
    private TextView ln;
    private TextView ph;
    private TextView date;
    private TextView em;
    private TextView mc;
    private TextView pos;
    private TextView pw;
    private String username;
    private boolean isManager;
    private String teamid;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);

        playerLoggedIn = getIntent().getExtras().getString("playerName");
        updateUser = ViewDeletePlayer.updateUser;
        result = ViewDeletePlayer.getResult();
        userList = ViewDeletePlayer.userList;

        downloadProfilePic(playerLoggedIn);
        img = (ImageView) findViewById(R.id.profilepic);

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this)
                    .withFilter(new ProgressFilter());
            Log.i("tag", "connection started ...woohoo");
            userTable = mClient.getTable(User.class);
        } catch (MalformedURLException e) {
            Log.i("tag", "error with mobile service connection");
            e.printStackTrace();
        }
        userTable = mClient.getTable(User.class);
        userList = new ArrayList<>();
        mProgressBar = (ProgressBar) findViewById(R.id.loadingProgressBar);
        // Initialize the progress bar
        mProgressBar.setVisibility(ProgressBar.GONE);

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            for (User item : result) {
                                if (item.getId().equals(playerLoggedIn)) {
                                    userList.add(item);

                                    for (int i = 0; i < userList.size(); i++) {
                                        un = (TextView) findViewById(R.id.username);
                                        un.setText(userList.get(i).getId());

                                        fn = (TextView) findViewById(R.id.firstname);
                                        fn.setText(userList.get(i).getFirstname());

                                        ln = (TextView) findViewById(R.id.lastname);
                                        ln.setText(userList.get(i).getLastname());

                                        ph = (TextView) findViewById(R.id.phone);
                                        ph.setText(userList.get(i).getPhonenumber());

                                        date = (TextView) findViewById(R.id.DOB);
                                        String s = userList.get(i).getDob();
                                        s = s.substring(0, Math.min(s.length(), 10));
                                        String original = s;
                                        String[] sp = original.split("-");
                                        String finalString = sp[2] + "-" + sp[1] + "-" + sp[0];
                                        date.setText(finalString);

                                        em = (TextView) findViewById(R.id.email);
                                        em.setText(userList.get(i).getEmail());

                                        mc = (TextView) findViewById(R.id.medicalcondition);
                                        mc.setText(userList.get(i).getMedicalcondition());

                                        pos = (TextView) findViewById(R.id.position);
                                        pos.setText(userList.get(i).getPosition());

                                        pw = (TextView) findViewById(R.id.password);
                                        pw.setText(userList.get(i).getPassword());


                                        isManager = userList.get(i).isIsmanager();
                                        teamid = userList.get(i).getTeamid();
                                    }
                                }
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


        Button update = (Button) findViewById(R.id.updatePlayer);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String firstname, lastname, phone, d, email, medCon, p, password;
                firstname = fn.getText().toString();
                lastname = ln.getText().toString();
                phone = ph.getText().toString();
                d = date.getText().toString();
                email = em.getText().toString();
                medCon = mc.getText().toString();
                p = pos.getText().toString();
                password = pw.getText().toString();
                User user = new User(playerLoggedIn,firstname, lastname,
                        password, d, medCon, isManager, phone, email, p, teamid);
                try {
                    updateUserProfile(user);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                finish();
                //startActivity(new Intent(UserEditProfile.this, UserHome.class));
                Log.i("TEST", "Test"+getUn());
            }
        });
    }

    public static void updateUserProfile(final User user) throws MalformedURLException {
        userTable = mClient.getTable(User.class);
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done;
                try {
                    userTable.update(user).get();
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


    public void downloadProfilePic(final String username) {

        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... par) {
                String done = "false";
                try{
                    // Retrieve storage account from connection-string.
                    CloudStorageAccount storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=http;" + "AccountName=footymanapp;" + "AccountKey=dh3Mh8Yz3ue1St4sx4QMv8tBb4nzb8OiemxfBkbvtx7EeDeTqBxTSHREcGkwhIIuJUvpmklZxV0jvFFD13I7QA==");

                    // Create the blob client.
                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                    // Retrieve reference to a previously created container.
                    CloudBlobContainer container = blobClient.getContainerReference("profilepics");

                    // Loop through each blob item in the container.
                    for (ListBlobItem blobItem : container.listBlobs()) {
                        // If the item is a blob, not a virtual directory.
                        CloudBlob blob = (CloudBlob) blobItem;
                        Log.i("blob",blob.getName() );
                        Log.i("blob",username );
                        if(blob.getName().equals(username+".jpg")){
                            blob.download(new FileOutputStream(Environment.getExternalStorageDirectory() + "/download/" + blob.getName())); // saved to downloads for access
                            done ="true";
                        }
                    }


                } catch (Exception e) {
                    done = "false";
                    e.printStackTrace();
                }
                return done;
            }
            protected void onPostExecute(String done) {
                if (done.equals("true")) {
                    Log.i( "download pic", "success");
                    img.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/download/" + username + ".jpg"));
                } else {
                    Log.i("download pic", "failed");

                }

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
            UserEditProfile.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}