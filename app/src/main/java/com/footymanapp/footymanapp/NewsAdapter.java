package com.footymanapp.footymanapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Keith on 27/04/2015.
 */
public class NewsAdapter extends ArrayAdapter<MessageToSend> {
    private ArrayList<MessageToSend> news;

    public NewsAdapter(Context context, ArrayList<MessageToSend> news) {
        super(context, R.layout.newslayout, news);
        this.news = news;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.newslayout, parent, false);
        vh = new ViewHolder();
        MessageToSend currentUser = getItem(position);
        vh.dateTextView = (TextView) rowView.findViewById(R.id.newsDate);
        vh.messageTextView = (TextView) rowView.findViewById(R.id.message);

        // Put the next lastname into the TextView
        vh.dateTextView.setText(currentUser.getDate());
        vh.messageTextView.setText(currentUser.getMessage());

        return rowView;
    }

    static class ViewHolder {
        protected TextView dateTextView;
        protected TextView messageTextView;
    }
}
//