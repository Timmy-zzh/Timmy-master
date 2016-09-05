package com.timmy.advance.customViewGroup;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.ui.base.BaseActivity;

public class ArcMenuActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arc_menu);
        initToolBar();

        ArcMenu arcMenu = (ArcMenu) findViewById(R.id.arc_menu);


        arcMenu.setOnItemMenuClickListener(new ArcMenu.OnItemMenuClickListener() {
            @Override
            public void onItemMenuClick(View view, int position) {
                Toast.makeText(ArcMenuActivity.this, "position--" + position + "--" + view.getTag(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
