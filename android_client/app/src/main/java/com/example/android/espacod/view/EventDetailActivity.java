package com.example.android.espacod.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.android.espacod.R;
import com.example.android.espacod.model.Event;

public class EventDetailActivity extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;

    static final String EVENT_LIST_VALUES = "eventListValues";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        loadComponents();
    }

    private void loadComponents() {
        this.mTitle = (TextView) findViewById(R.id.event_title);
        this.mDescription = (TextView) findViewById(R.id.event_description);
    }
}