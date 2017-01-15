package com.timmy.highUI.searchView;

import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.timmy.R;
import com.timmy.library.util.Logger;
import com.timmy.library.util.Toast;

public class SearchViewActivity extends AppCompatActivity {

    private static final java.lang.String TAG = "SearchViewActivity";
    //    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SearchView");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Logger.d(TAG, "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_searcher_view, menu);
        //在这个方法里拿到searchview对象－注意menu文件中的ActionViewClass前缀使用app：
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        //一开始searchview就处于显示状态
        searchView.setIconified(false);
        //false表示searchview不能隐藏，默认为true
//        searchView.setIconifiedByDefault(false);
        //通过abc_search_view.xml文件布局，可以拿到里面的控件-提交按钮
        ImageView icon = (ImageView) searchView.findViewById(R.id.search_go_btn);
//        icon.setImageResource(android.support.v7.appcompat.R.drawable.abc_ic_voice_search_api_material);
        icon.setImageResource(R.mipmap.ic_launcher);
        icon.setVisibility(View.VISIBLE);
//        icon.setMaxWidth(200);
        //设置
        searchView.setSubmitButtonEnabled(true);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.toastShort("提交");
            }
        });

        //获取到输入框
        SearchView.SearchAutoComplete et = (SearchView.SearchAutoComplete) searchView.findViewById(R.id.search_src_text);
        et.setHint("请输入搜索内容");
        et.setHintTextColor(Color.GREEN);



        //设置搜索输入框的输入监听
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //搜索提交
                Logger.d(TAG, "onQueryTextSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Logger.d(TAG, "onQueryTextChange: " + newText);
                return false;
            }
        });

        searchView.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Toast.toastShort("焦点改变");
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.toastShort("关闭监听");
                return false;
            }
        });


        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.toastShort("setOnSearchClickListener");
            }
        });

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Logger.d(TAG, "onOptionsItemSelected");
//        if (item.getItemId() == R.id.action_search){
//            searchView.setIconified(false);
//            searchView.setIconifiedByDefault(false);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }
}
