package com.timmy.advance.customViewGroup;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

import java.util.Arrays;
import java.util.List;

/**
 * 自定义ScrollView实现的效果为ScrollView内部有很多Item,item的个数由用户决定,高度平均分配屏幕高度.
 * 当Item滑动时判断滑动高度是否超过item高度一半,未超过一半,重置为0
 * <p>
 * 实现原理:ScrollView内部保持一个LinearLayout容器,保持Item+1个,并且动态的增加减少Item.
 */
public class AdapterScrollViewActivity extends BaseActivity {

    private LayoutInflater mInflater;
    private Integer[] colorArray = new Integer[]{Color.BLUE, Color.CYAN, Color.DKGRAY, Color.RED};
    private List<Integer> colorList = Arrays.asList(colorArray);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adapter_scroll_view);
        initToolBar();
        mInflater = LayoutInflater.from(this);

        AdapterScrollView adapterScrollView = (AdapterScrollView) findViewById(R.id.adapter_scroll_view);

        ScrollAdapter adapter = new ScrollAdapter() {

            @Override
            public View getView(AdapterScrollView parent, int position) {
                View itemView = mInflater.inflate(R.layout.item_scroll_view, null);
                TextView text = (TextView) itemView.findViewById(R.id.tv_text);
                text.setText("postion:" + position);
                itemView.setBackgroundColor(colorList.get(position));
                return itemView;
            }

            @Override
            public int getCount() {
                return 4;
            }
        };

        adapterScrollView.setAdapter(adapter);


    }
}
