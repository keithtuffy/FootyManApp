package com.footymanapp.footymanapp;

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

class CustomAdapter extends ArrayAdapter<PlayerSubs>
{
    private Context mContext;
    public CustomAdapter(Context context, ArrayList<PlayerSubs> names)
    {
        super(context, R.layout.listviewlayout, names);
        this.mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PlayerSubs users = getItem(position);
        View rowView = convertView;

        if(rowView==null)
        {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.listviewlayout, parent, false);
        }
        final TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        // Put the next lastname into the TextView
        textViewName.setText(users.getName());
        return rowView;
    }

}
