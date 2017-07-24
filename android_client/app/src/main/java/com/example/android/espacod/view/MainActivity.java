package com.example.android.espacod.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;
import com.example.android.espacod.R;
import com.example.android.espacod.adapter.EventsAdapter;
import com.example.android.espacod.model.Event;
import com.example.android.espacod.util.UrlJsonAsyncTask;
import com.example.android.espacod.util.Util;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.category;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static final String EVENT_LIST_VALUES = "eventListValues";

    FloatingActionButton fab;
    boolean fb_open = false;
    LinearLayout mFbMenu;
    ListView mLvEvents;
    private List<Event> mEvents;
    private EventsAdapter mEventAdapter;
    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = getSharedPreferences("CurrentUser", MODE_PRIVATE);

        mLvEvents = (ListView) findViewById(R.id.events_list);
        mEvents = new ArrayList<Event>();

        if (savedInstanceState != null) {
            mEvents = savedInstanceState.getParcelableArrayList(EVENT_LIST_VALUES);
        }

        mEventAdapter = new EventsAdapter(this,  mEvents);
        mLvEvents.setAdapter(mEventAdapter);

        if (Util.isNetworkAvailable(this)) {
            fetchEvents();
        } else {
            Util.showToast(this, getString(R.string.no_internet_connection));
        }

        mFbMenu = (LinearLayout) findViewById(R.id.fb_menu);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(fb_open) {
                    fab.startAnimation(
                            AnimationUtils.loadAnimation(MainActivity.this, R.anim.back_rotate) );
                    mFbMenu.setVisibility(View.GONE);
                } else {
                    fab.startAnimation(
                            AnimationUtils.loadAnimation(MainActivity.this, R.anim.rotate) );
                    mFbMenu.setVisibility(View.VISIBLE);
                }
                fb_open = !fb_open;
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void fetchEvents() {
        FetchEventsTask fetchEventsTask = new FetchEventsTask(MainActivity.this, MainActivity.this);
        fetchEventsTask.setMessageLoading("Loading events...");
        fetchEventsTask.setAuthToken(mPreferences.getString("AuthToken", ""));
        fetchEventsTask.execute();
    }

    private void updateEventList(JSONArray data) {
        mEventAdapter.clear();

        for (int i = 0; i < data.length(); i++) {
            try {
                JSONObject row = data.getJSONObject(i);
                int id = row.getInt("id");
                String title = row.getString("title");
                String category = row.getString("category");
                String description = row.getString("description");

               mEventAdapter.add(new Event(id, title, description, category));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_events) {

        } else if (id == R.id.nav_all_events) {

        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_my_events) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_contact) {

        } else if (id == R.id.nav_logout) {
            SharedPreferences.Editor editor = mPreferences.edit();
            editor.remove("AuthToken");
            editor.apply();

            Intent intent = new Intent(MainActivity.this,
                    SplashActivity.class);
            startActivityForResult(intent, 0);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList(EVENT_LIST_VALUES, (ArrayList<Event>) mEvents);
        super.onSaveInstanceState(savedInstanceState);
    }

    private class FetchEventsTask extends UrlJsonAsyncTask {

        private static final String ALL_EVENTS_API_ENDPOINT_URL = "https://infinite-bayou-64424.herokuapp.com/events";
        private final String LOG_TAG = FetchEventsTask.class.getSimpleName();
        private final MainActivity mainActivity;

        public FetchEventsTask(Context context, MainActivity mainActivity) {
            super(context);
            this.mainActivity = mainActivity;
        }

        @Override
        protected JSONObject doInBackground(String... params) {

            Uri builtUri = Uri.parse(ALL_EVENTS_API_ENDPOINT_URL);

            URL url = Util.createUrl(builtUri.toString());

            String jsonResponse = null;
            try {
                jsonResponse = Util.makeHttpRequest(url, "GET", mPreferences.getString("AuthToken", ""));
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error closing input stream", e);
            }

            if (TextUtils.isEmpty(jsonResponse)) {
                return null;
            }

            JSONObject baseJsonResponse = new JSONObject();
            try {
                baseJsonResponse = baseJsonResponse.put("data", new JSONArray(jsonResponse));

            } catch (JSONException e) {
                Log.e(LOG_TAG, "Problem parsing the book list JSON results", e);
            }

            return baseJsonResponse;
        }

        @Override
        protected void onPostExecute(JSONObject json) {
            if (json.has("data")) {
                try {
                    JSONArray data = json.getJSONArray("data");
                    if (data.length() > 0) {
                        Log.i(LOG_TAG, data.toString());
                        mainActivity.updateEventList(data);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}