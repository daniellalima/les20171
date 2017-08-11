package com.example.android.espacod.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.espacod.R;
import com.example.android.espacod.util.UrlJsonAsyncTask;
import com.example.android.espacod.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.example.android.espacod.util.Util.LOG_TAG;

public class EventRegisterActivity extends AppCompatActivity {

    private Spinner mCategorySpinner;
    private Button mEventRequestButton;
    private TextView mEventTitleField;
    private TextView mEventDescriptionField;
    private SharedPreferences mPreferences;
    private String mCategory;
    private String mTitle;
    private String mDescription;
    private String mDate;
    private String mTime;
    private TextView mEventTimeField;
    private TextView mEventDateField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_register);

        mEventTitleField = (TextView) findViewById(R.id.register_event_title);
        mEventDescriptionField = (TextView) findViewById(R.id.register_event_description);
        mCategorySpinner = (Spinner) findViewById(R.id.spinner_category);
        mEventTimeField = (TextView) findViewById(R.id.event_time);
        mEventDateField = (TextView) findViewById(R.id.event_date);
        mEventRequestButton = (Button) findViewById(R.id.event_request);
        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        setupSpinner();

        mEventRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTitle = mEventTitleField.getText().toString();
                mDescription = mEventDescriptionField.getText().toString();
                mDate = mEventDateField.getText().toString();
                mTime = mEventTimeField.getText().toString();

                if (mTitle.trim().length() == 0 || mDescription.trim().length() == 0 || mCategory.trim().length() == 0 ||
                        mTime.trim().length() == 0 || mDate.trim().length() == 0) {
                    Toast.makeText(EventRegisterActivity.this, "Please complete all the fields", Toast.LENGTH_LONG).show();
                } else {
                    sendEventRequest();
                }
            }
        });
    }

    private void sendEventRequest() {
        EventRequestTask requestTask = new EventRequestTask(EventRegisterActivity.this);
        requestTask.setMessageLoading("Enviando requisição...");

        requestTask.execute(new String[] {mTitle, mDescription, mCategory, mDate, mTime});
    }

    private void setupSpinner() {
        List<String> categories = new ArrayList<String>();
        categories.add("Palestra");
        categories.add("Workshop");

        ArrayAdapter<String> categorySpinnerAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, categories);

        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mCategorySpinner.setAdapter(categorySpinnerAdapter);

        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals("Palestra")) {
                        mCategory = "Palestra";
                    } else if (selection.equals("Workshop")) {
                        mCategory = "Workshop";
                    } else {
                        mCategory = "Unknown";
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mCategory = "Unknown";
            }
        });
    }

    private class EventRequestTask extends UrlJsonAsyncTask {

        private final static String EVENT_REQUEST_API_ENDPOINT_URL = "https://infinite-bayou-64424.herokuapp.com/events";
        private static final String DESCRIPTION_PARAM = "description";
        private static final String CATEGORY_PARAM = "category";
        private String TITLE_PARAM = "title";
        private final static String DATE_PARAM = "date";

        public EventRequestTask(Context context) {
            super(context);
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String title = params[0];
            String description = params[1];
            String category = params[2];
            String date = params[3];
            String time = params[4];

            Uri builtUri = Uri.parse(EVENT_REQUEST_API_ENDPOINT_URL).buildUpon()
                    .appendQueryParameter(TITLE_PARAM, title)
                    .appendQueryParameter(DESCRIPTION_PARAM, description)
                    .appendQueryParameter(CATEGORY_PARAM, category)
                    .appendQueryParameter(DATE_PARAM, date + "T" + time).build();

            URL url = Util.createUrl(builtUri.toString());

            String jsonResponse = null;
            try {
                jsonResponse = Util.makeHttpRequest(url, "POST", mPreferences.getString("AuthToken", ""));
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }

            if (TextUtils.isEmpty(jsonResponse)) {
                return null;
            }

            JSONObject baseJsonResponse = new JSONObject();
            try {
                baseJsonResponse = baseJsonResponse.put("data", jsonResponse);

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the event list JSON results", e);
            }

            return baseJsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            try {
                if (json.has("data")) {
                    Toast.makeText(context, "Evento solicitado com sucesso!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(EventRegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(context, "Erro ao solicitar evento!", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, e.getMessage().toString(), Toast.LENGTH_LONG).show();
            } finally {
                super.onPostExecute(json);
            }
        }
    }
}
