package com.footymanapp.footymanapp;

import android.os.AsyncTask;
import android.widget.ArrayAdapter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import org.w3c.dom.Text;

class CustomAdapter extends ArrayAdapter<User> {
    public CustomAdapter(Context context, ArrayList<User> names) {
        super(context, R.layout.listviewlayout, names);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.listviewlayout,parent,false);

        User currentUser = getItem(position);
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        // Put the next lastname into the TextView
        textViewName.setText(currentUser.getFirstname());


        return rowView;
    }
}
