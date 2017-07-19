package com.timmy.project.softKeyboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.timmy.R;
import com.timmy.Util;
import com.timmy.base.BaseActivity;
import com.timmy.highUI.recyclerview.adapter.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SoftKeyboardActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
//        setContentView(R.layout.activity_soft_keyboard);
        setContentView(R.layout.activity_weixin);
        initToolBar();

//        Button mBtnLogin = (Button) findViewById(R.id.btn_login);
//        mBtnLogin.setOnClickListener(this);
//        showKeyboard(mBtnLogin);
        initView();
    }

    private void initView() {
        List<String> lists = new ArrayList<>();
        for (int i = 1; i <= 25; i++) {
            lists.add("Item " + i);// + "-" + str.substring(random.nextInt(str.length())));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new SimpleAdapter(this,lists ));

    }

    /**
     * 关闭虚拟键盘
     *
     * @param view
     */
    private void showKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    @Override
    public void onClick(View v) {
        showKeyboard(v);
    }
}
