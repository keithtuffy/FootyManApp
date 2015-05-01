package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceException;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Keith on 01/04/2015.
 */
public class RegisterPlayer extends ActionBarActivity {

    private MobileServiceClient mClient;
    private MobileServiceTable<User> userTable;
    private ImageView profilePic;
    private boolean fromCamera = false;
    private final String picType = "profilepics";
    private ArrayList<String> aList;
    private String username;
    private String teamid;
    private EditText un;
    private TextView fn;
    private TextView ln;
    private TextView ph;
    private TextView date;
    private TextView em;
    private TextView mc;
    private TextView pos;
    private TextView pw;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);
        aList = new ArrayList<>();

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", RegisterPlayer.this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        userTable = mClient.getTable(User.class);
        usernameInUse();
       //image
        profilePic = (ImageView) findViewById(R.id.profilepic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageIntent();


            }

        });


        final TextView dateEdit = (TextView) findViewById(R.id.DOB);
        dateEdit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker;
                mDatePicker = new DatePickerDialog(RegisterPlayer.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        // TODO Auto-generated method stub
                        selectedmonth = selectedmonth + 1;
                        dateEdit.setText(selectedyear + "-" + String.format("%02d", selectedmonth) + "-" + String.format("%02d", selectedday));
                    }
                    public void onDateSelectedButtonClick()
                    {

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
            }
        });
        final TextView position = (TextView) findViewById(R.id.position);
        position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] pos = {"Goalkeeper", "Defender", "Midfield", "Forward"};
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPlayer.this);
                builder.setTitle("Choose Position")
                        .setItems(pos, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                position.setText(pos[which]);
                            }
                        });
                builder.show();
            }
        });
        Button regPlayer = (Button) findViewById(R.id.registerPlayer);
        regPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    un = (EditText) findViewById(R.id.username);
                    username = un.getText().toString();

                    fn = (TextView) findViewById(R.id.firstname);
                    String firstname = fn.getText().toString();

                    ln = (TextView) findViewById(R.id.lastname);
                    String lastname = ln.getText().toString();

                    ph = (TextView) findViewById(R.id.phone);
                    String phone = ph.getText().toString();

                    date = (TextView) findViewById(R.id.DOB);
                    String DOB = date.getText().toString();

                    em = (TextView) findViewById(R.id.email);
                    String email = em.getText().toString();

                    mc = (TextView) findViewById(R.id.medicalcondition);
                    String medicalcondition = mc.getText().toString();

                    pos = (TextView) findViewById(R.id.position);
                    String position = pos.getText().toString();

                    pw = (TextView) findViewById(R.id.password);
                    String password = pw.getText().toString();


                final boolean ismanager = Boolean.valueOf(getIntent().getExtras().getString("ismanager"));
                final String teamname = getIntent().getExtras().getString("teamname");
                teamid = teamname;

                //usernameInUse();
                if (username.length() == 0) {
                    un.setError("Please enter a username");
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                }

                else if (firstname.length() == 0) {
                    fn.setError("Please enter a first name");
                    un.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                } else if (lastname.length() == 0) {
                    ln.setError("Please enter a last name");
                    un.setError(null);
                    fn.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                } else if (phone.length() == 0) {
                    ph.setError("Please enter a phone number");
                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                } else if (email.length() == 0 || !email.contains("@")) {
                    em.setError("Please enter a valid email address");
                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                } else if (DOB.length() == 0) {
                    date.setError("Please enter DOB");
                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                } else if (medicalcondition.length() == 0) {
                    mc.setError("Please enter medical condition");
                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                } else if (position.length() == 0) {
                    pos.setError("Please enter a position");
                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pw.setError(null);
                } else if (password.length() == 0) {
                    pw.setError("Please enter a password");
                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                }else if(userExists(username))
                {
                    un.setError("Username already in use!");
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);
                }
                else
                {
                    User user = new User(username, firstname, lastname, password, DOB, medicalcondition, ismanager, phone, email, position, teamname);
                    try {
                        DatabaseQueries.addUser(user, RegisterPlayer.this);
                        playerCreationAlert(ismanager);
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }

                    un.setError(null);
                    fn.setError(null);
                    ln.setError(null);
                    ph.setError(null);
                    date.setError(null);
                    em.setError(null);
                    mc.setError(null);
                    pos.setError(null);
                    pw.setError(null);

                    un.setText("");
                    fn.setText("");
                    ln.setText("");
                    ph.setText("");
                    date.setText("");
                    em.setText("");
                    mc.setText("");
                    pos.setText("");
                    pw.setText("");

                     // save picture in azure
                    DatabaseQueries.setStorageConnecton(picType);
                    DatabaseQueries.addPic(picPath, username + ".jpg", fromCamera, RegisterPlayer.this, picType);
                }
            }
        });

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
        final Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");

        // Add the camera options.
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, 1);
    }

    @Override

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
                    profilePic.setImageURI(outputFileUri);
                    fromCamera = true;
                } else {

                    picPath = data.getData().toString();
                    Log.i("test data",picPath);
                    Log.i("test output", outputFileUri.getPath());
                    profilePic.setImageURI(data.getData());
                }
            }
        }
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

    public void playerCreationAlert(final boolean ismanager) {
        AlertDialog.Builder playerAlert = new AlertDialog.Builder(this);
        playerAlert.setMessage("Congratulations,\nYour Profile has been created").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if(ismanager == true){
                    Intent addPlayer = new Intent(RegisterPlayer.this, AdminHome.class);
                    addPlayer.putExtra("ismanager", "true");
                    addPlayer.putExtra("teamName", teamid);
                    startActivity(addPlayer);
                }


                finish();

            }
        }).create();
        playerAlert.show();
    }
    public void usernameInUse()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.execute().get();

                            for (User item : result)
                            {
                                {
                                    aList.add(item.getId());
                                }
                            }

                }catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (MobileServiceException e) {
                    e.printStackTrace();
                }
                return null;
            }
        }.execute();
    }

    public boolean userExists(String username)
    {
        boolean exists = false;
        for(int i = 0;i<aList.size();i++)
        {
            if(username.equals(aList.get(i)))
            {
                exists = true;
            }
        }
        return exists;
    }


}
