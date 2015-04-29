//package com.footymanapp.footymanapp;
//import android.net.Uri;
//import android.os.AsyncTask;
//import android.os.Bundle;
//import android.os.Environment;
//import android.support.v7.app.ActionBarActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.ProgressBar;
//import android.widget.TextView;
//
//import com.google.common.util.concurrent.ListenableFuture;
//import com.google.common.util.concurrent.SettableFuture;
//import com.microsoft.azure.storage.CloudStorageAccount;
//import com.microsoft.azure.storage.blob.CloudBlob;
//import com.microsoft.azure.storage.blob.CloudBlobClient;
//import com.microsoft.azure.storage.blob.CloudBlobContainer;
//import com.microsoft.azure.storage.blob.ListBlobItem;
//import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
//import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
//import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
//import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
//import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
//
//import java.io.FileOutputStream;
//import java.util.ArrayList;
//
///**
// * Created by prend_000 on 15/04/2015.
// */
//public class EditPlayerDetails extends ActionBarActivity {
//    private ArrayList<User> userList;
//    int result;
//    private User updateUser;
//    private ImageView img;
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_user_details);
//
//
//
//        updateUser = ViewDeletePlayer.updateUser;
//        result = ViewDeletePlayer.getResult();
//        userList = ViewDeletePlayer.userList;
//
//        downloadProfilePic(updateUser.getId());
//        img = (ImageView) findViewById(R.id.profilepic);
//
//        TextView un = (TextView) findViewById(R.id.username);
//        un.setText(updateUser.getId());
//
//        TextView fn = (TextView) findViewById(R.id.firstname);
//        fn.setText(userList.get(result).getFirstname());
//
//        TextView ln = (TextView) findViewById(R.id.lastname);
//        ln.setText(userList.get(result).getLastname());
//
//        TextView ph = (TextView) findViewById(R.id.phone);
//        ph.setText(userList.get(result).getPhonenumber());
//
//        TextView date = (TextView) findViewById(R.id.DOB);
//        date.setText(userList.get(result).getDob());
//
//        TextView em = (TextView) findViewById(R.id.email);
//        em.setText(userList.get(result).getEmail());
//
//        TextView mc = (TextView) findViewById(R.id.medicalcondition);
//        mc.setText(userList.get(result).getMedicalcondition());
//
//        TextView pos = (TextView) findViewById(R.id.position);
//        pos.setText(userList.get(result).getPosition());
//
//        TextView pw = (TextView) findViewById(R.id.password);
//        pw.setText(userList.get(result).getPassword());
//
//
//    }
//
//
//    public void downloadProfilePic(final String username) {
//
//        new AsyncTask<Void, Void, String>() {
//            @Override
//            protected String doInBackground(Void... par) {
//                String done = "false";
//                try{
//                    // Retrieve storage accou from connection-string.
//                    CloudStorageAccount storageAccount = CloudStorageAccount.parse("DefaultEndpointsProtocol=http;" + "AccountName=footymanapp;" + "AccountKey=dh3Mh8Yz3ue1St4sx4QMv8tBb4nzb8OiemxfBkbvtx7EeDeTqBxTSHREcGkwhIIuJUvpmklZxV0jvFFD13I7QA==");
//
//                    // Create the blob client.
//                    CloudBlobClient blobClient = storageAccount.createCloudBlobClient();
//
//                    // Retrieve reference to a previously created container.
//                    CloudBlobContainer container = blobClient.getContainerReference("profilepics");
//
//                    // Loop through each blob item in the container.
//                    for (ListBlobItem blobItem : container.listBlobs()) {
//                        // If the item is a blob, not a virtual directory.
//                        CloudBlob blob = (CloudBlob) blobItem;
//                        Log.i("blob",blob.getName() );
//                        Log.i("blob",username );
//                        if(blob.getName().equals(username+".jpg")){
//                            blob.download(new FileOutputStream(Environment.getExternalStorageDirectory() + "/download/" + blob.getName())); // saved to downloads for access
//                            done ="true";
//                        }
//                    }
//
//
//                } catch (Exception e) {
//                    done = "false";
//                    e.printStackTrace();
//                }
//                return done;
//            }
//            protected void onPostExecute(String done) {
//                if (done.equals("true")) {
//                    Log.i( "download pic", "success");
//                } else {
//                    Log.i("download pic", "failed");
//
//                }
//                img.setImageURI(Uri.parse(Environment.getExternalStorageDirectory() + "/download/" + username + ".jpg"));
//            }
//        }.execute();
//    }
//
//
//}