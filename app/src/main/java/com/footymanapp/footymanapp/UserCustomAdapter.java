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
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.microsoft.windowsazure.mobileservices.MobileServiceList;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import org.w3c.dom.Text;

class UserCustomAdapter extends ArrayAdapter<User> {
    private ArrayList<User> names;
    public UserCustomAdapter(Context context, ArrayList<User> names) {
        super(context, R.layout.listviewlayout, names);
        this.names = names;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View rowView = inflater.inflate(R.layout.listviewlayout, parent, false);
        vh = new ViewHolder();
        User currentUser = getItem(position);
        vh.firstNameTextView = (TextView) rowView.findViewById(R.id.firstNameTextView);
        vh.lastNameTextView = (TextView) rowView.findViewById(R.id.lastNameTextView);
        vh.checkbox = (CheckBox) rowView.findViewById(R.id.checkBoxList);
        // Put the next lastname into the TextView
        vh.firstNameTextView.setText(currentUser.getFirstname());
        vh.lastNameTextView.setText(currentUser.getLastname());
        vh.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int getPosition = (Integer) buttonView.getTag();  // Here we get the position that we have set for the checkbox using setTag.
                names.get(getPosition).setSelected(buttonView.isChecked()); // Set the value of checkbox to maintain its state.
            }
        });
        vh.checkbox.setTag(position);
        vh.checkbox.setChecked(names.get(position).isSelected());
        if(currentUser.isSubspaid() == false)
        {
            vh.checkbox.setChecked(false);
        }
        return rowView;
    }

    static class ViewHolder {
        protected TextView firstNameTextView;
        protected TextView lastNameTextView;
        protected CheckBox checkbox;
    }


}
