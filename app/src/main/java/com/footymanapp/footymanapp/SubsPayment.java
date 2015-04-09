package com.footymanapp.footymanapp;

import android.app.ListActivity;
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

public class SubsPayment extends ListActivity {
    private ArrayList<User> names;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subspayment);

        //String[] name = {"a","s","d","f","g","h","j","y","r","e","w","q"};

        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(getListView().getContext(), R.layout.listviewlayout, name);
        //getListView().setAdapter(adapter);

        names = new ArrayList<>(DatabaseQueries.getUser());

        //userListView = (ListView) findViewById(android.R.id.list);
        //userListView.setAdapter (new InnerCustomAdapter(this, name));
        }
    }

/*class InnerCustomAdapter  {
    private String[] array;
    private Context mContext;

    public InnerCustomAdapter(Context context, String[] array)
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
        String currentUser = toString();
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
            // Put the next lastname into the TextView
        textViewName.setText(currentUser);

        return rowView;
    }*/

