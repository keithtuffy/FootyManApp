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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

class CustomAdapter extends ArrayAdapter<User> {
    private ArrayList<User> array;
    private Context mContext;

    public CustomAdapter(Context context, ArrayList<User> array)
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
        User users = getItem(position);
        if(users != null)
        {
            final TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
            // Put the next lastname into the TextView
            textViewName.setText(users.getLastname());
        }
        return rowView;
    }

}
