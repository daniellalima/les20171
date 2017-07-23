package com.example.android.espacod.adapter;

import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import com.example.android.espacod.R;
import com.example.android.espacod.model.Event;
import java.util.List;

public class EventsAdapter extends BaseAdapter {

    List<Event> mList;
    LayoutInflater inflater;

    public EventsAdapter(Context context, List<Event> mList) {
        this.mList = mList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.event_item, parent, false);
        return convertView;
    }
}