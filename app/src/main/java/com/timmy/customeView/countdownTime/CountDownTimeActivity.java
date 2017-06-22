package com.timmy.customeView.countdownTime;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.timmy.R;

public class CountDownTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_down_time);

        final CountDownTimeView countDownTimeView = (CountDownTimeView) findViewById(R.id.countdown_view);
        countDownTimeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimeView.startCountDown();
            }
        });

//        countDownTimeView.startCountDown();
    }
}
