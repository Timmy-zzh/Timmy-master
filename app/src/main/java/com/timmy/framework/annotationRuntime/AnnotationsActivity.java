package com.timmy.framework.annotationRuntime;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.framework.annotationRuntime.annotations.ContentView;
import com.timmy.framework.annotationRuntime.annotations.OnViewClick;
import com.timmy.framework.annotationRuntime.annotations.ViewInject;
import com.timmy.library.util.Toast;

/**
 * 注解:通过学习注解和反射,实现运行时代码注解框架
 * 注解使用:
 * 1.setContentView(id)
 * 2.findViewById(id)
 * 3.setOnclickListener()
 *
 * 步骤:
 * 1.先定义注解文件
 * 2.使用在类上添加注解
 * 3.在成员变量上添加注解
 * 4.在方法上添加注解-设置点击事件的注解
 */
@ContentView(R.layout.activity_annotations)
public class AnnotationsActivity extends BaseActivity {

    @ViewInject(R.id.tv_text)
    TextView textView;
    @ViewInject(R.id.btn)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Activity
//        setContentView(R.layout.activity_annotations);
        ViewInjectUtils.inject(this);

        initToolBar();
        textView.setText("修改后的文本");
        //注解实现点击交互功能
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.toastShort("按钮点击");
//            }
//        });

    }

    @OnViewClick({R.id.tv_text,R.id.btn})
    public void viewClick(View view){
        switch (view.getId()){
            case R.id.tv_text:
                Toast.toastShort("文本点击");
                break;
            case R.id.btn:
                Toast.toastShort("按钮点击");
                break;
        }
    }

}
