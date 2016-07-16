package com.timmy.actionbar.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.timmy.actionbar.R;

/**
 * ActionBar遮罩效果
 * Tab实现
 * 二级界面返回箭头问题
 *
 * *******单元测试*******
 * 进行函数级别的测试
 *
 */
public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void nextActivity(View view) {
        switch (view.getId()){
            case R.id.btn_next:
                startActivity(new Intent(this, SecondActivity.class));

                break;
            case R.id.btn_fragments:
                startActivity(new Intent(this, FragmentsActivity.class));

                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                Toast.makeText(this, getResources().getString(R.string.search), Toast.LENGTH_SHORT).show();

                return true;
            case R.id.menu_settting:
                Toast.makeText(this, getResources().getString(R.string.setting), Toast.LENGTH_SHORT).show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
