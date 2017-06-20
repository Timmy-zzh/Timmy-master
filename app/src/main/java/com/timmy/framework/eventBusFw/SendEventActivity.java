package com.timmy.framework.eventBusFw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.eventBusFw.EventBus.TimmyEventBus;

public class SendEventActivity extends BaseActivity {

    private Button button;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        initToolBar();
        button = (Button) findViewById(R.id.btn);
        textView = (TextView) findViewById(R.id.tv_content);
        button.setText("post发送事件");
    }

    public void jump(View view){
        TimmyEventBus.getDefault().post(new Person("timmy",26));
    }
}
