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

    DatabaseQueries db = new DatabaseQueries();
    private ArrayList<User> lastNames;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subspayment);
        lastNames = db.getUser();
        ListView listView = (ListView) findViewById(R.id.subspaymentList);

        for(int i = 0;i < lastNames.size();i++)
        {
            Log.i("TAG", "SUCCESS " + lastNames.get(i).getLastname());
        }
        CustomAdapter cAdapter = new CustomAdapter(this, lastNames);
        listView.setAdapter(cAdapter);
        }
    }

