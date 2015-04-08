package com.footymanapp.footymanapp;

import android.app.ListActivity;
import android.content.ClipData;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
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
import android.os.Handler;

public class SubsPayment extends ActionBarActivity {

    ArrayList<User> lastNames;
    ListView userListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subspayment);

        lastNames = new ArrayList<>(DatabaseQueries.getUser());
        //Log.i("UserTable", "First Name: " + lastNames.get(0).getFirstname());
        userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter (new CustomAdapter(this, lastNames));
        }
    }

class InnerCustomAdapter extends ArrayAdapter<User> {
    private ArrayList<User> array = new ArrayList<>();
    private Context mContext;

    public InnerCustomAdapter(Context context, ArrayList<User> array)
    {
        super(context, R.layout.listviewlayout);
        this.array = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listviewlayout, parent, false);
        }
        User currentUser = array.get(position);
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
            // Put the next lastname into the TextView
        textViewName.setText(currentUser.getLastname());

        return rowView;
    }

}

