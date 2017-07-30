package com.example.android.espacod.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.android.espacod.R;

import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;

public class EventRegisterActivity extends AppCompatActivity {

    private Spinner mCategorySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);

        setupSpinner();
    }

    private void setupSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("Palestra");
        categories.add("Workshop");

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(categorySpinnerAdapter);
    }
}
