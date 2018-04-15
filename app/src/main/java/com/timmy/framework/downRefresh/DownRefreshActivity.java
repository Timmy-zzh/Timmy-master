package com.timmy.framework.downRefresh;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.timmy.R;
import com.timmy.base.SimpleAdapter;
import com.timmy.highUI.recyclerview.decoration.DividerItemDecoration;
import com.timmy.home.MainContentAdapter;
import com.timmy.home.model.MainModel;
import com.timmy.home.model.MainTag;

import java.util.ArrayList;
import java.util.List;

public class DownRefreshActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_refresh);
    }

    public void systemRefresh(View view) {
        startActivity(new Intent(this,SwipeRefreshActivity.class));
    }

    public void customRefresh(View view) {
        startActivity(new Intent(this,CustomRefreshActivity.class));
    }
}
