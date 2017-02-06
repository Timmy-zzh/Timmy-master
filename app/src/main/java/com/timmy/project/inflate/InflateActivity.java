package com.timmy.project.inflate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.timmy.R;

public class InflateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inflate);

        //1.先获取到Activity的布局view,拿到根view
        LinearLayout rootView = (LinearLayout) findViewById(R.id.root_layout);
        //2.通过inflate方法将view_inflate_inner.xml布局添加到activity的根view中
        LayoutInflater layoutInflater = LayoutInflater.from(this);
//        View inflateView = layoutInflater.inflate(R.layout.view_inflate_inner, rootView, true);


        View inflateView = layoutInflater.inflate(R.layout.view_inflate_inner, null);

        rootView.addView(inflateView);

    }
}
