package com.example.android.espacod.view;

import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;

        import android.os.Bundle;
        import android.support.design.widget.FloatingActionButton;
        import android.view.View;
        import android.support.design.widget.NavigationView;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.animation.AnimationUtils;
        import android.widget.LinearLayout;
        import android.widget.ListView;

import com.example.android.espacod.R;
import com.example.android.espacod.adapter.EventsAdapter;
import com.example.android.espacod.model.Event;

import java.util.ArrayList;
        import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FloatingActionButton fab;
    boolean fb_open = false;
    LinearLayout mFbMenu;
    ListView mLvEvents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLvEvents = (ListView) findViewById(R.id.events_list);
        List<Event> eventLists = new ArrayList<Event>();
        eventLists.add(new Event(0,""));
        eventLists.add(new Event(0,""));
        eventLists.add(new Event(0,""));
        eventLists.add(new Event(0,""));
        mLvEvents.setAdapter(new EventsAdapter(this, eventLists));


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

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}