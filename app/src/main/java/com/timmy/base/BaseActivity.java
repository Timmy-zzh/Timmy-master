package com.timmy.base;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.timmy.AppManager;
import com.timmy.R;

/**
 * Created by Administrator on 2016/3/21 0021.
 */
public class BaseActivity extends AppCompatActivity {


    private TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManager.getInstance().addActivity(this);
    }

    /***
     * 标题
     */
    public void initToolBar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setSubtitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        //设置返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    /**
     * 设置Toolbar文字居中
     * @param title
     */
    protected void setToolbarTitle(String title){
        if (mToolbarTitle!= null){
            mToolbarTitle.setText(title);
        }
    }

    @Override
    public void finish() {
        super.finish();
        //设置动画
        overridePendingTransition(R.anim.anim_enter, R.anim.anim_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    public void startIntent(Class<?> clazz, Bundle Bundle) {
        Intent starter = new Intent(this, clazz);
        if (Bundle != null) {
            starter.putExtras(Bundle);
        }
        startActivity(starter);
    }

    public void startIntent(Class<?> clazz) {
        startIntent(clazz, null);
    }


    /**
     * 通过类名启动Activity
     *
     * @param pClass
     */
    protected void openActivity(Class<?> pClass) {
        openActivity(pClass, null);
    }

    /**
     * 通过类名启动Activity，并且含有Bundle数据
     *
     * @param pClass
     * @param pBundle
     */
    protected void openActivity(Class<?> pClass, Bundle pBundle) {
        Intent intent = new Intent(this, pClass);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    /**
     * 通过Action启动Activity
     *
     * @param pAction
     */
    protected void openActivity(String pAction) {
        openActivity(pAction, null);
    }

    /**
     * 通过Action启动Activity，并且含有Bundle数据
     *
     * @param pAction
     * @param pBundle
     */
    protected void openActivity(String pAction, Bundle pBundle) {
        Intent intent = new Intent(pAction);
        if (pBundle != null) {
            intent.putExtras(pBundle);
        }
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppManager.getInstance().killActivity(this);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


}
