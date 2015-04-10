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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

public class SubsPayment extends ListActivity {
    public ArrayList<User> userList;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subspayment);

        userList = new ArrayList<>();
        userList.add(new User("1", "Dave", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Dave","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));

        ListAdapter theAdapter = new ArrayAdapter<User>(this, R.layout.listviewlayout,R.id.textViewName, userList);


        //Log.i("Tag", "TEST" + userList.get(0).getLastname());
        ListView userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter(theAdapter);
        }
    }

class InnerCustomAdapter extends ArrayAdapter<User> {
//    private ArrayList<User> array;
//    private Context mContext;

    public InnerCustomAdapter(Context context, ArrayList<User> names) {
        super(context, R.layout.listviewlayout, names);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.listviewlayout,parent,false);

        User currentUser = getItem(position);
        TextView textViewName = (TextView) rowView.findViewById(R.id.textViewName);
        // Put the next lastname into the TextView
        textViewName.setText(currentUser.getLastname());

        return rowView;
    }
}

