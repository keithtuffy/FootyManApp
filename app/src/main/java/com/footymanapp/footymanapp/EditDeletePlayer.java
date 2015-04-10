package com.footymanapp.footymanapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by prend_000 on 10/04/2015.
 */
public class EditDeletePlayer extends ActionBarActivity
{
    private ArrayList<User> userList;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_delete);

        userList = new ArrayList<>();
        userList.add(new User("1", "Dave", "Prendy", "password", "30-08-1986", "none", false, "12345", "prendy@fuckyou.com", "Striker", "Newbridge"));
        userList.add(new User("1","Keith","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Kevin","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","John","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Andy","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Alan","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Joe","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Pete","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Ian","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));
        userList.add(new User("1","Denis","Prendy", "password","30-08-1986","none",false,"12345","prendy@fuckyou.com","Striker","Newbridge"));

        ListAdapter theAdapter = new CustomAdapter(this, userList);
        ListView listView = (ListView) findViewById(android.R.id.list);
        listView.setAdapter(theAdapter);

        Button editPlayer = (Button) findViewById(R.id.editButton);
        editPlayer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(EditDeletePlayer.this, RegisterPlayer.class));
            }
        });

        Button deletePlayer = (Button) findViewById(R.id.deleteButton);
        deletePlayer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
