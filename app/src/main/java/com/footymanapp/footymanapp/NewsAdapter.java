package com.footymanapp.footymanapp;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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

//        SimpleDateFormat fromUser = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat myFormat = new SimpleDateFormat("dd-MM-yyyy @ hh:mm");
//
//
//        String reformattedStr = null;
//        try {
//            reformattedStr = myFormat.format(fromUser.parse(currentUser.getDate()));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        vh.dateTextView.setPaintFlags(vh.dateTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
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