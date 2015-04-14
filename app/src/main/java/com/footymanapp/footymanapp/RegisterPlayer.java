package com.footymanapp.footymanapp;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Keith on 01/04/2015.
 */
public class RegisterPlayer extends ActionBarActivity {


    ImageView profilePic;
    Uri outputFileUri;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_player);
        final Uri[] fix = new Uri[1];
        profilePic = (ImageView) findViewById(R.id.profilepic);
        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final File root = new File(Environment.getExternalStorageDirectory() + File.separator + "Pictures/" + "Footyman" + File.separator);
                Log.i("root", root.toString());
                if (!root.exists()) {
                    root.mkdirs();
                    Log.i("root", root.toString());
                }

                final String fname = "img_" + System.currentTimeMillis() + ".jpg";
                final File sdImageMainDirectory = new File(root, fname);
                fix[0] = Uri.fromFile(sdImageMainDirectory);
                //Log.i("output", RegisterPlayer.this.outputFileUri.toString());

                // Camera.
                final List<Intent> cameraIntents = new ArrayList<Intent>();
                final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                final PackageManager packageManager = getPackageManager();
                final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
                for (ResolveInfo res : listCam) {
                    final String packageName = res.activityInfo.packageName;
                    final Intent intent = new Intent(captureIntent);
                    intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    intent.setPackage(packageName);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
                    cameraIntents.add(intent);
                }


                Intent pic = new Intent();
                pic.setType("image/*");
                pic.setAction(Intent.ACTION_GET_CONTENT);
                Intent chooser = Intent.createChooser(pic, "Select Profile Picture");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));
                startActivityForResult(chooser, 1);

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
                        dateEdit.setText(String.format("%02d", selectedday) + "-" + String.format("%02d", selectedmonth) + "-" + selectedyear);
                        Log.i("TEST", "WORKED");
                    }

                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
                mDatePicker.show();
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.showSoftInput(dateEdit, InputMethodManager.SHOW_IMPLICIT);
            }
        });

        TextView pos = (TextView)findViewById(R.id.position);
        final String[] positions = {"Goalkeeper","Defender","Midfield","Forward"};
        pos.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(RegisterPlayer.this);
                builder.setTitle("Choose Position")
                        .setItems(positions, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                TextView position = (TextView) findViewById(R.id.position);
                                position.setText(positions[which]);
                                // Create the AlertDialog object and return it
                            }
                        });
                builder.show();
            }
        });

        Button regPlayer = (Button) findViewById(R.id.registerPlayer);
        regPlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                TextView un = (TextView) findViewById(R.id.username);
                String username = un.getText().toString();

                TextView fn = (TextView) findViewById(R.id.firstname);
                String firstname = fn.getText().toString();

                TextView ln = (TextView) findViewById(R.id.lastname);
                String lastname = ln.getText().toString();

                TextView ph = (TextView) findViewById(R.id.phone);
                String phone = ph.getText().toString();

                TextView date = (TextView) findViewById(R.id.DOB);
                String DOB = date.getText().toString();

                TextView em = (TextView) findViewById(R.id.email);
                String email = em.getText().toString();

                TextView mc = (TextView) findViewById(R.id.medicalcondition);
                String medicalcondition = mc.getText().toString();

                TextView pos = (TextView) findViewById(R.id.position);
                String position = pos.getText().toString();

                TextView pw = (TextView) findViewById(R.id.password);
                String password = pw.getText().toString();


                final boolean ismanager = Boolean.valueOf(getIntent().getExtras().getString("ismanager"));
                final String teamname = getIntent().getExtras().getString("teamname");

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
                } else if (firstname.length() == 0) {
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
                } else {
                    User user = new User(username, firstname, lastname, password, DOB, medicalcondition, ismanager, phone, email, position, teamname);
                    DatabaseQueries.addUser(user);
                    playerCreationAlert();

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
                }
            }
        });

        outputFileUri = fix[0];
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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

                    Log.i("camera", outputFileUri.toString());

                } else {
                    profilePic.setImageURI(data.getData());
                }
            }
        }
    }


    public void playerCreationAlert() {
        AlertDialog.Builder playerAlert = new AlertDialog.Builder(this);
        playerAlert.setMessage("Congratulations,\nYour Profile has been created").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Intent addPlayer = new Intent(RegisterPlayer.this, AdminHome.class);
                addPlayer.putExtra("ismanager", "false");
                addPlayer.putExtra("teamname", "Newbridge");
                startActivity(addPlayer);

                finish();

            }
        }).create();
        playerAlert.show();
    }

    public class PositionDialogFragment extends DialogFragment {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the Builder class for convenient dialog construction
            final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Choose Position")
                    .setItems(R.array.positions, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            final TextView position = (TextView) findViewById(R.id.position);
                            position.setText(which);
                            // Create the AlertDialog object and return it
                        }
                    });
            return builder.create();
        }
    }
}
