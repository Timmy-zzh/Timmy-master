package com.timmy.highUI.path;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * Path的使用
 */
public class PathUseActicity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_use);
        initToolBar();

        WaveView waveView = (WaveView) findViewById(R.id.wave_view);
        waveView.startWaveAnimate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_path_measure,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_path_measure){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
