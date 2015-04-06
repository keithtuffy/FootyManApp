package com.footymanapp.footymanapp;

import android.app.ListActivity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.footymanapp.footymanapp.R;
import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;
import com.microsoft.windowsazure.mobileservices.table.TableQueryCallback;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SubsPayment extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subspayment);
        DatabaseQueries.readLastName();
        //ArrayList<String> names = PlayerSubs.getUsers();
       // names = PlayerSubs.getUsers();
        //String[] names = {"dave", "alan","keith","andy","frank","bob","ian","wendy","bren"};
        ArrayList<PlayerSubs> names = PlayerSubs.getUsers();
        ListAdapter cAdapter = new CustomAdapter(this, names);
        ListView listView = (ListView) findViewById(R.id.subspaymentList);
        //Log.i("TAG LIST Contents", "HELLO" + names.get(0));
        listView.setAdapter(cAdapter);
    }
}
    class PlayerSubs
    {
        public String name;
        public PlayerSubs(String name)
        {
            this.name = name;
        }
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static ArrayList<PlayerSubs> getUsers()
        {
            ArrayList<PlayerSubs> users = new ArrayList<PlayerSubs>();
            users.add(new PlayerSubs("Harry"));
            users.add(new PlayerSubs("Marla"));
            users.add(new PlayerSubs("Sarah"));
            users.add(new PlayerSubs("Keith"));
            users.add(new PlayerSubs("Dave"));
            users.add(new PlayerSubs("Ian"));
            users.add(new PlayerSubs("John"));
            users.add(new PlayerSubs("Denis"));
            users.add(new PlayerSubs("Wanker"));
            users.add(new PlayerSubs("Al"));
            users.add(new PlayerSubs("Andy"));
            users.add(new PlayerSubs("John"));
            return users;
        }

    }
