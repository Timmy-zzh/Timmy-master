package com.timmy.advance;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.timmy.R;
import com.timmy.advance.scrollvertical.VerticalScrollActivity;
import com.timmy.advance.slidelist.FollowSlideListActivity;
import com.timmy.ui.base.BaseActivity;
import com.timmy.ui.base.BaseRecyclerViewAdapter;
import com.timmy.ui.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AdvanceActivity extends BaseActivity {

    @Bind(R.id.rv_recycleView)
    RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private BaseRecyclerViewAdapter adapter;
    private List<String> mData = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance);
        ButterKnife.bind(this);
        initToolBar();
        initView();
        initData();
    }

    private void initView() {
        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        adapter = new AdvanceAdapter(this);
        mRecyclerView.setAdapter(adapter);
    }

    private void initData() {
        mData.add("仿腾讯List滑动删除");
        mData.add("使用Activity作为Dialog来展示");
        mData.add("Android自定义ViewGroup实现竖向引导界面");


        adapter.setData(mData);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_advance, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_dialog) {
            startActivity(new Intent(this, DialogActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    class AdvanceAdapter extends BaseRecyclerViewAdapter<String> {
        public AdvanceAdapter(Context context) {
            super(context);
        }

        @Override
        protected int inflaterItemLayout(int viewType) {
            return R.layout.item_tab_pager;
        }

        @Override
        protected void bindData(BaseViewHolder holder, int position, String s) {
            holder.setText(R.id.tv_content, s);
            holder.setVisibility(R.id.tv_type, View.GONE);
        }

        @Override
        protected void onItemClickListener(View itemView, int position, String s) {
            switch (position) {
                case 0:
                    AdvanceActivity.this.openActivity(FollowSlideListActivity.class);
                    break;
                case 1:
                    AdvanceActivity.this.openActivity(DialogActivity.class);
                    break;
                case 2:
                    AdvanceActivity.this.openActivity(VerticalScrollActivity.class);
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
