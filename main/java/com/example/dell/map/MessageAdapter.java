package com.example.dell.map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yogesh on 12/11/17.
 */

public class MessageAdapter extends ArrayAdapter<User> {
    public MessageAdapter(Context context, int resource, List<User> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null)
        {
            convertView = ((Activity)getContext()).getLayoutInflater().inflate(R.layout.list,parent,false);
        }

        TextView nameTextView = (TextView)convertView.findViewById(R.id.nameTextView);
        TextView locationTextView = (TextView)convertView.findViewById(R.id.locationTextView);


        User name = getItem(position);

        nameTextView.setText(name.getName());
      //  locationTextView.setText(name.getLocation());

        return convertView;

    }

}