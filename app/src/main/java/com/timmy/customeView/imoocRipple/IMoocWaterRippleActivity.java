package com.timmy.customeView.imoocRipple;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SeekBar;

import com.timmy.R;
import com.timmy.base.BaseActivity;

/**
 * 高仿慕课网下拉刷新水波纹
 */
public class IMoocWaterRippleActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imooc_water_ripple);

        final ImoocRippleView rippleView = (ImoocRippleView) findViewById(R.id.ripple_view);

        rippleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rippleView.startRippleMove();
            }
        });

        SeekBar seekBar = (SeekBar) findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rippleView.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
