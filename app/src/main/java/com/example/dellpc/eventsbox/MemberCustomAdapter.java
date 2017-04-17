package com.example.dellpc.eventsbox;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MemberCustomAdapter extends ArrayAdapter {
    private String names[];
    private String phones[];
    private LayoutInflater inflator;
    public MemberCustomAdapter(Context context, String names[], String phones[]) {
        super(context, R.layout.row_memberlist, names);

        this.names=names;
        this.phones=phones;
        inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listViewItem = inflator.inflate(R.layout.row_memberlist, null);
        TextView textViewName = (TextView) listViewItem.findViewById(R.id.memberNameTV);
        TextView textViewContact = (TextView) listViewItem.findViewById(R.id.memberContactTV);
        ImageView image = (ImageView) listViewItem.findViewById(R.id.imageView);

        textViewName.setText(names[position]);
        textViewContact.setText(phones[position]);
        return listViewItem;
    }
}