package com.example.android.espacod.adapter;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.espacod.R;
import com.example.android.espacod.model.Event;
import java.util.List;

public class EventsAdapter extends ArrayAdapter<Event> {

    public EventsAdapter(Context context, List<Event> mEventList) {
        super(context, 0, mEventList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Event event = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.event_item, parent, false);
        }


        return convertView;
    }
}