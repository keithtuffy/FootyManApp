package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


public class RegisterTeam extends ActionBarActivity {

    private ImageView teamPic;
    private boolean fromCamera = false;
    private final String picType = "teampics";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_team);


        // team crest pic
        teamPic = (ImageView) findViewById(R.id.crestpic);
        teamPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();


            }

        });

        // when the register team button is pressed
        Button regTeam = (Button) findViewById(R.id.registerTeam);
        regTeam.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                TextView tn = (TextView) findViewById(R.id.firstname);
                String teamname = tn.getText().toString();

                TextView em = (TextView) findViewById(R.id.email);
                String email = em.getText().toString();

                TextView ph = (TextView) findViewById(R.id.phone);
                String phone = ph.getText().toString();

                TextView mn = (TextView) findViewById(R.id.managerName);
                String managername = mn.getText().toString();

                TextView ag = (TextView) findViewById(R.id.agegroup);
                String agegroup = ag.getText().toString();


                String latitude = String.valueOf(MapsActivity.getLatitude());
                String longitude = String.valueOf(MapsActivity.getLongitude());

                //validtion
                if(teamname.length() == 0){
                    tn.setError("Please enter a team name");
                }
                else if(email.length() == 0|| !email.contains("@")){
                    em.setError("Please enter a valid email");
                    tn.setError(null);
                    ph.setError(null);
                    mn.setError(null);
                    ag.setError(null);
                }
                else if(phone.length() == 0){
                    ph.setError("Please enter a phone number");
                    tn.setError(null);
                    em.setError(null);
                    mn.setError(null);
                    ag.setError(null);
                }
                else if(managername.length() == 0){
                    mn.setError("Please enter a manager name");
                    tn.setError(null);
                    em.setError(null);
                    ph.setError(null);
                    ag.setError(null);
                }
                else if(agegroup.length() == 0){
                    ag.setError("Please enter an age group");
                    tn.setError(null);
                    em.setError(null);
                    ph.setError(null);
                    mn.setError(null);
                }
                else if(latitude.length() == 0){
                    Toast toast = Toast.makeText(RegisterTeam.this, "Please set a location", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else {

                    Team team = new Team(teamname, email, phone, managername, agegroup, latitude, longitude);
                    Log.i("teamname", tn.getText().toString());


                    try {
                        DatabaseQueries.addTeam(team, RegisterTeam.this);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    tn.setError(null);
                    em.setError(null);
                    ph.setError(null);
                    mn.setError(null);
                    ag.setError(null);


                    tn.setText("");
                    em.setText("");
                    ph.setText("");
                    mn.setText("");
                    ag.setText("");
                    teamCreationAlert(teamname);

                    DatabaseQueries.setStorageConnecton(picType);
                    DatabaseQueries.addPic(picPath,teamname+".jpg", fromCamera, RegisterTeam.this, picType);
                }
            }
        });



        // used to search googlemap for pitch location
        TextView pitchLocation = (TextView) findViewById(R.id.pitchlocation);
        pitchLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v)
            {
                   startActivity(new Intent(RegisterTeam.this, MapsActivity.class));

            }
        }




    );}


    public void teamCreationAlert(final String teamname)
    {
        AlertDialog.Builder teamAlert = new AlertDialog.Builder(this);
        teamAlert.setMessage("\t\tCongratulations!!\nYour Team has been created. Press 'OK' to create your profile").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(RegisterTeam.this,RegisterPlayer.class);
                intent.putExtra("ismanager", "true");
                intent.putExtra("teamname",teamname);
                startActivity(intent);
                finish();
                dialog.dismiss();

            }
        }).create();
        teamAlert.show();
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
            RegisterTeam.this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private String getRealPathFromURI(Uri uri) {
        if (uri.getScheme().toString().compareTo("content")==0)
        {
            Cursor cursor =getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst())
            {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//Instead of "MediaStore.Images.Media.DATA" can be used "_data"
                Uri filePathUri = Uri.parse(cursor.getString(column_index));
                String file_name = filePathUri.getLastPathSegment().toString();
                String file_path=filePathUri.getPath();
                Toast.makeText(this, "File Name & PATH are:" + file_name + "\n" + file_path, Toast.LENGTH_LONG).show();
            }
        }
        return" ";
    }

    private Uri outputFileUri;
    private String picPath;

    private void openImageIntent() {

// Determine Uri of camera image to save.

        final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "footyman" + File.separator);
        root.mkdirs();
        final String fname = "img_"+ System.currentTimeMillis() + ".jpg";
        final File sdImageMainDirectory = new File(root, fname);
        outputFileUri = Uri.fromFile(sdImageMainDirectory);
        // outputFileUri = sdImageMainDirectory.getPath();
        Log.i("testsd", sdImageMainDirectory.getPath());
        picPath = Environment.getExternalStorageDirectory() + File.separator + "footyman" + File.separator + fname;


        // Camera.
        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            cameraIntents.add(intent);
        }

        // Filesystem.
        final Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        // Chooser of filesystem options.
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source For Image");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, 1);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                final boolean isCamera;
                if (data == null) {
                    isCamera = true;
                } else {
                    final String action = data.getAction();
                    if (action == null) {
                        isCamera = false;
                    } else {
                        isCamera = action.equals(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                    }
                }


                if (isCamera) {
                    teamPic.setImageURI(outputFileUri);
                    fromCamera = true;
                } else {

                    picPath = data.getData().toString();
                    Log.i("test data",picPath);
                    Log.i("test output", outputFileUri.getPath());
                    teamPic.setImageURI(data.getData());
                }
            }
        }
    }
}

