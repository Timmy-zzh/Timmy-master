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
