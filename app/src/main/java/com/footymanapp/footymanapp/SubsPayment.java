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
        userList.add(new User("1", "Keith", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Kevin", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "John", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Andy", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Alan", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Joe", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Pete", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Ian", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1", "Denis", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));

        ListAdapter theAdapter = new CustomAdapter(this, userList);

        ListView userListView = (ListView) findViewById(android.R.id.list);
        userListView.setAdapter(theAdapter);
    }
}

