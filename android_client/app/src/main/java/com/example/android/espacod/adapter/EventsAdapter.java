package com.example.android.espacod.adapter;

import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        TextView titleTextView = (TextView) convertView.findViewById(R.id.title_text_view);
        titleTextView.setText(event.getmTitle());

        TextView authorsTextView = (TextView) convertView.findViewById(R.id.description_text_view);
        authorsTextView.setText(event.getmDescription());

        return convertView;
    }
}