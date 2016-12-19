package com.timmy.highUI.slideslip;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.highUI.recyclerview.autoPoll.AutoPollRecyclerActivity;
import com.timmy.highUI.recyclerview.wrapRecyclerView.InteractiveRecyclerView;
import com.timmy.library.util.Logger;

public class DrawerLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideslip);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerToggle.syncState();
        drawerLayout.addDrawerListener(drawerToggle);

        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View content = drawerLayout.findViewById(R.id.content_layout);
                Logger.d("slideOffset--", slideOffset + "--");

            }
        });

        //
//        drawerLayout.setDrawerShadow();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_slip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_slip:
                Util.gotoNextActivity(this, NavigationDemoActivity.class);
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }
}
