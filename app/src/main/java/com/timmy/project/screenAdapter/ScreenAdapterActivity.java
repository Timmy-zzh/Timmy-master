package com.timmy.project.screenAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.timmy.R;

/**
 * 屏幕适配方案
 */
public class ScreenAdapterActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_adapter);
        Log.d(TAG, "onCreate");

//        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
//        Logger.d("widthPixels: " + displayMetrics.widthPixels);
//        Logger.d("heightPixels: " + displayMetrics.heightPixels);
//        Logger.d("density: " + displayMetrics.density);
        ClassA aa = new ClassB();
        ClassB bb = new ClassB();

        Log.d(TAG, "aa.a:  " + aa.a);
        Log.d(TAG, "bb.a:  " + bb.a);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public void nextAction(View view) {
        startActivity(new Intent(this, SecondActivity.class));
    }
}
