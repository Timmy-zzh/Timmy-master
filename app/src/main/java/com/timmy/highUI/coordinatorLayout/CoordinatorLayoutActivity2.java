package com.timmy.highUI.coordinatorLayout;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.highUI.coordinatorLayout.behavior.CustomeBehaviorActivity;
import com.timmy.library.util.Toast;

public class CoordinatorLayoutActivity2 extends AppCompatActivity {

    private TabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordinator_layout2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floating_action_button);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        adapter = new TabPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(v, "FAB onClick", Snackbar.LENGTH_SHORT);
                snackbar.setAction("点击", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.toastShort("点击 SnackBar Action");
                    }
                });
                snackbar.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coordinator, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            Util.gotoNextActivity(CoordinatorLayoutActivity2.this, CoordinatorLayoutActivity3.class);
            return true;
        } else if (item.getItemId() == R.id.menu_behavior) {
            Util.gotoNextActivity(CoordinatorLayoutActivity2.this, CustomeBehaviorActivity.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class TabPagerAdapter extends FragmentStatePagerAdapter {
        private String tabTitles[] = new String[]{"TAB1", "TAB2", "TAB3"};

        public TabPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TabPageFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }
    }


}
