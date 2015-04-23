package com.footymanapp.footymanapp;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by prend_000 on 15/04/2015.
 */
public class EditPlayerDetails extends ActionBarActivity {
    private ArrayList<User> userList;
    int result;
    private User updateUser;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_details);


        updateUser = EditDeletePlayer.updateUser;
        result = EditDeletePlayer.getResult();
        userList = EditDeletePlayer.userList;

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

        TextView pw = (TextView) findViewById(R.id.password);
        pw.setText(userList.get(result).getPassword());

        DatabaseQueries.setBlobString();
        DatabaseQueries.downloadProfilePic(updateUser.getId());

//        Button update = (Button) findViewById(R.id.updatePlayer);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AsyncTask<Void, Void, Void>() {
//
//                    @Override
//                    protected Void doInBackground(Void... params) {
//                        try
//                        {
//                            userTable.where(user.getId() == un).insert(user);
//                        }
//
//
//
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//                    return null;
//                }
//            }.execute();
//        }
//            }
//        });
//    }
    }
}