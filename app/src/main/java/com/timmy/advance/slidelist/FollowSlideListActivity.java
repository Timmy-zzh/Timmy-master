package com.timmy.advance.slideList;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.timmy.R;
import com.timmy.library.util.Toast;
import com.timmy.base.BaseActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 高仿腾讯ListView滑动删除
 */
public class FollowSlideListActivity extends BaseActivity {

    @Bind(R.id.slide_listview)
    SlideDeleteListView mListView;
    private List<String> mDatas;
    private String[] strLists = new String[]{"HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
            "Hibernate", "Spring", "HTML5", "Javascript", "Lucene"};
    private ListAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_slide_list);
        ButterKnife.bind(this);
        initToolBar();
        initData();
    }

    private void initData() {
//        mDatas = Arrays.asList(strLists);
        // 不要直接Arrays.asList
        mDatas = new ArrayList<String>(Arrays.asList("HelloWorld", "Welcome", "Java", "Android", "Servlet", "Struts",
                "Hibernate", "Spring", "HTML5", "Javascript", "Lucene"));

//        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mDatas);
//        mListView.setAdapter(adapter);

        adapter = new ListAdapter(this);
        adapter.setData(mDatas);
        mListView.setAdapter(adapter);


        mListView.setOnDeleteButtonClickListener(new SlideDeleteListView.OnDeleteButtonClickListener() {
            @Override
            public void deleteBtn(int position) {
                Toast.toastShort("deleteBtn--" + position);
                adapter.remove(adapter.getItem(position));
//                adapter.notifyDataSetChanged();
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.toastShort("onItemClick--" + position);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
