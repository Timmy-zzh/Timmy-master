package com.timmy.highUI.path;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * Path的使用
 */
public class PathUseActicity extends BaseActivity {

    private PathLoadingView loadingView;
    private Button mBtnSuccess;
    private Button mBtnFail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_use);
        initToolBar();
        initView();

//        WaveView waveView = (WaveView) findViewById(R.id.wave_view);
//        waveView.startWaveAnimate();

//        final PathMeasureUseView view = new PathMeasureUseView(this);
//        setContentView(view);

//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                view.startMove();
//            }
//        });
    }

    private void initView() {
        loadingView = (PathLoadingView) findViewById(R.id.loading_view);
        mBtnSuccess = (Button) findViewById(R.id.btn_success);
        mBtnFail = (Button) findViewById(R.id.btn_fail);

        mBtnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.paySuccess();
            }
        });

        mBtnFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingView.payFail();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_path_measure,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_path_measure){

            return true;
        }else if (itemId == R.id.menu_path){

            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
