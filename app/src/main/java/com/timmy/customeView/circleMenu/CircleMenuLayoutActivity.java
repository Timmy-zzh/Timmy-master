package com.timmy.customeView.circleMenu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.library.util.Toast;

public class CircleMenuLayoutActivity extends AppCompatActivity {

    int[] icons = {R.mipmap.composer_button, R.mipmap.composer_camera,
            R.mipmap.composer_icn_plus, R.mipmap.composer_music,
            R.mipmap.composer_sleep, R.mipmap.composer_with};
    String[] texts = {"SAFK", "KSAHF", "氨分解", "阿道夫", "阿道夫", "扣扣哦"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_menu_layout);
        CircleMenuLayout circleMenuLayout = findViewById(R.id.menu_layout);
        circleMenuLayout.setAdapter(new MenuAdapter() {
            @Override
            public View getView(int position, View view, ViewGroup parent) {
                return LayoutInflater.from(CircleMenuLayoutActivity.this).inflate(R.layout.item_text_image, parent, false);
            }

            @Override
            public void setItemView(View itemView, int index) {
                ImageView imageView = itemView.findViewById(R.id.iv_image);
                TextView textView = itemView.findViewById(R.id.tv_text);
                imageView.setImageResource(R.mipmap.ic_launcher);
                textView.setText(texts[index]);
            }

            @Override
            public int getCount() {
                return texts.length;
            }
        });

//        circleMenuLayout.setMenuItemIconsAndTexts(icons, texts);
//
        circleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(View v, int position) {
                Toast.toastShort("postion:" + position);
            }
        });

    }
}
