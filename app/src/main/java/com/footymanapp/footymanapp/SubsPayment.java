package com.footymanapp.footymanapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class SubsPayment extends ActionBarActivity {
    private ArrayList<User> names;
    ListView userListView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subspayment);

        names = new ArrayList<>(DatabaseQueries.getUser());

        Log.i("UserTable", "First Name: " + names.get(0).getFirstname());

        userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter (new CustomAdapter(this, names));
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

