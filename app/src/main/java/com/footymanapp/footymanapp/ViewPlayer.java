package com.footymanapp.footymanapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.CloudBlob;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.ListBlobItem;

import java.io.FileOutputStream;
import java.util.ArrayList;

/**
 * Created by prend_000 on 22/04/2015.
 */
public class ViewPlayer extends ActionBarActivity
{
    private ArrayList<User> userList;
    int result;
    private User updateUser;
    private ImageView img;
    private String username;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_player);
        updateUser = ViewDeletePlayer.updateUser;
        result = ViewDeletePlayer.getResult();
        userList = ViewDeletePlayer.userList;
        img = (ImageView) findViewById(R.id.profilepic);



        TextView un = (TextView) findViewById(R.id.username);
        un.setText(updateUser.getId());
        username = updateUser.getId();
        downloadProfilePic(username);

        TextView fn = (TextView) findViewById(R.id.firstname);
        fn.setText(userList.get(result).getFirstname());

        TextView ln = (TextView) findViewById(R.id.lastname);
        ln.setText(userList.get(result).getLastname());

        TextView ph = (TextView) findViewById(R.id.phone);
        ph.setText(userList.get(result).getPhonenumber());

        TextView date = (TextView) findViewById(R.id.DOB);
        String s = userList.get(result).getDob();
        s = s.substring(0, Math.min(s.length(), 10));
        String original = s;
        String[] sp = original.split("-");
        String finalString = sp[2] + "-" + sp[1] + "-" + sp[0];
        date.setText(finalString);

        TextView em = (TextView) findViewById(R.id.email);
        em.setText(userList.get(result).getEmail());

        TextView mc = (TextView) findViewById(R.id.medicalcondition);
        mc.setText(userList.get(result).getMedicalcondition());

        TextView pos = (TextView) findViewById(R.id.position);
        pos.setText(userList.get(result).getPosition());

        ImageView img = (ImageView) findViewById(R.id.profilepic);


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
                    img.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/download/" + username+".jpg"));
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
            ViewPlayer.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
