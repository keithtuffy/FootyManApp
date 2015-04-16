package com.footymanapp.footymanapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import java.net.MalformedURLException;
import java.util.ArrayList;

/**
 * Created by prend_000 on 10/04/2015.
 */
public class EditDeletePlayer extends ActionBarActivity {
    public ArrayList<User> userList;
    private ArrayList<User> editList;
    public UserCustomAdapter theAdapter;
    private static MobileServiceClient mClient;
    private static MobileServiceTable<User> userTable;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        try {
            mClient = new MobileServiceClient("https://footymanapp.azure-mobile.net/", "sTbAnGoYQuyPjURPFYCgKKXSvugGfZ89", this);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        userTable = mClient.getTable(User.class);
        userList = new ArrayList<>();
        editList = new ArrayList<>();
        theAdapter = new UserCustomAdapter(this, userList);
        getUser();
        final ListView userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter(theAdapter);

        Button editPlayer = (Button) findViewById(R.id.editButton);
        editPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                for (int i = 0; i < userList.size(); i++) {
                    if (userList.get(i).isSelected()) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected Void doInBackground(Void... params) {
                                try {
                                    final MobileServiceList<User> result = DatabaseQueries.userTable.where().field("ismanager").eq(false).execute().get();
                                    runOnUiThread(new Runnable() {

                                        @Override
                                        public void run() {
                                            for (User item : result) {
                                                editList.add(item);
                                            }
                                        }
                                    });
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return null;
                            }
                        }.execute();

                        TextView un = (TextView) findViewById(R.id.username);
                        un.setText(editList.get(i).getId());

                        TextView fn = (TextView) findViewById(R.id.firstname);
                        fn.setText(editList.get(i).getFirstname());

                        TextView ln = (TextView) findViewById(R.id.lastname);
                        ln.setText(editList.get(i).getLastname());

                        TextView ph = (TextView) findViewById(R.id.phone);
                        ph.setText(editList.get(i).getPhonenumber());

                        TextView date = (TextView) findViewById(R.id.DOB);
                        date.setText(editList.get(i).getDob());

                        TextView em = (TextView) findViewById(R.id.email);
                        em.setText(editList.get(i).getEmail());

                        TextView mc = (TextView) findViewById(R.id.medicalcondition);
                        mc.setText(editList.get(i).getMedicalcondition());

                        TextView pos = (TextView) findViewById(R.id.position);
                        pos.setText(editList.get(i).getPosition());

                        TextView pw = (TextView) findViewById(R.id.password);
                        pw.setText(editList.get(i).getPassword());

                        //Log.i("CheckBox Test", userList.get(i).getFirstname());
                    }
                    startActivity(new Intent(EditDeletePlayer.this, EditPlayerDetails.class));
                }
            }
        });
    Button deletePlayer = (Button) findViewById(R.id.deleteButton);
    deletePlayer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        for(int i = 0;i<userList.size();i++) {
            if (userList.get(i).isSelected()){
                checkItem(userList.remove(i));
                //refreshItemsFromTable();
                Log.i("CheckBox Test", userList.get(i).getFirstname());
            }
        }
    }
});

}
    public void getUser()
    {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.where().field("ismanager").eq(false).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            theAdapter.clear();
                            for (User item : result) {
                                theAdapter.add(item);
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
    }

    public void checkItem(final User item) {
        if (mClient == null) {
            return;
        }

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    userTable.delete(item);
                    runOnUiThread(new Runnable() {
                        public void run() {
                            if(item.isSelected()) {
                                theAdapter.remove(item);
                            }
                            //refreshItemsFromTable();
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
    public void setUserDetails()
    {

    }
    private void refreshItemsFromTable() {

        // Get the items that weren't marked as completed and add them in the
        // adapter
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    final MobileServiceList<User> result = userTable.where().field("selected").eq(false).execute().get();
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            theAdapter.clear();

                            for (User item : result) {
                                theAdapter.add(item);
                            }
                        }
                    });
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                return null;
            }
        }.execute();
    }
}
