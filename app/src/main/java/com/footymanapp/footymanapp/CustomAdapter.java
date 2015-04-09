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

import org.w3c.dom.Text;

class CustomAdapter extends ArrayAdapter<User> {
    private List<User> lastnames;
    private Context mContext;

    public CustomAdapter(Context context, List<User> array) {
        super(context, R.layout.listviewlayout, array);
        this.mContext = context;
        this.lastnames = array;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View rowView = convertView;
        ViewHolder holder;
        if (convertView == null)
        {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.listviewlayout, null);
            holder = new ViewHolder();
            holder.lname = (TextView) convertView.findViewById(R.id.textViewName);
            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        User userObj = lastnames.get(position);

        String lastname = userObj.getLastname();
        // Put the next lastname into the TextView
        holder.lname.setText(lastname);

        return convertView;
    }


    public static class ViewHolder{
        TextView lname;
    }

}

/*
* User users = getItem(position);
        if(users != null)
        {
            final TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
            // Put the next lastname into the TextView
            textViewName.setText(users.getLastname());
        }
        return rowView;*/
