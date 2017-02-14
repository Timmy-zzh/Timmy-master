package com.timmy.highUI.toast;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.base.BaseActivity;

public class ToastActivity extends BaseActivity {

    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toast);
        initToolBar();
    }

    public void toastClick(View view) {
        switch (view.getId()) {
            case R.id.btn1:
                Toast.makeText(this, "优化前-111", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn2:
                Toast.makeText(this, "优化前-222", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn3:
                strongToast("优化后-333");
                break;
            case R.id.btn4:
                strongToast("优化后-444");
                break;
        }
    }

    public void strongToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }
}
