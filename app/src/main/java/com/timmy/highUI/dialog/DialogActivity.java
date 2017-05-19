package com.timmy.highUI.dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class DialogActivity extends BaseActivity {

    private int selectPosition;
    String[] fruitArray = new String[]{"西瓜", "芒果", "哈密瓜", "荔枝", "火龙果", "波罗蜜"};
    boolean[] selectArray = new boolean[]{true, false, true, true, true, false};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog);
        ButterKnife.bind(this);
        initToolBar();
    }

    @OnClick({R.id.btn_ok, R.id.btn_choice_double, R.id.btn_choice_three,
            R.id.btn_choice_only, R.id.btn_choice_only2, R.id.btn_choice_more,
            R.id.btn_custom, R.id.btn_dialog_theme})
    public void alertDialog(View view) {
        switch (view.getId()) {
            case R.id.btn_ok:
                AlertDialog okDialog = new AlertDialog.Builder(this)
                        .setTitle("标题")
                        .setMessage("内容")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                okDialog.show();

                break;
            case R.id.btn_choice_double:
                AlertDialog dChoiceDialog = new AlertDialog.Builder(this)
                        .setTitle("标题")
                        .setMessage("内容")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dChoiceDialog.show();

                break;
            case R.id.btn_choice_three:
                AlertDialog tChoiceDialog = new AlertDialog.Builder(this)
                        .setTitle("请选择")
                        .setIcon(R.mipmap.ic_launcher)
                        .setMessage("你喜欢吃下列哪种水果?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setNeutralButton("查看详情", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                tChoiceDialog.show();
                break;
            case R.id.btn_choice_only:
                AlertDialog onlyChoiceDialog = new AlertDialog.Builder(this)
                        .setTitle("你喜欢吃下列哪种水果?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(fruitArray, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(DialogActivity.this, "你选择了" + fruitArray[i], Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                onlyChoiceDialog.show();
                break;
            case R.id.btn_choice_only2:
                AlertDialog onlyChoiceDialog2 = new AlertDialog.Builder(this)
                        .setTitle("你喜欢吃下列哪种水果?")
                        .setIcon(R.mipmap.ic_launcher)
                        .setSingleChoiceItems(fruitArray, 0, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectPosition = i;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(DialogActivity.this, "你选择了" + fruitArray[selectPosition], Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                onlyChoiceDialog2.show();
                break;
            case R.id.btn_choice_more:
                AlertDialog moreChoiceDialog = new AlertDialog.Builder(this)
                        .setMultiChoiceItems(fruitArray, selectArray, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                selectArray[i] = b;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(DialogActivity.this, "你选择了", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                moreChoiceDialog.show();
                break;
            case R.id.btn_custom:
                LayoutInflater layoutInflate = LayoutInflater.from(this);
                View customView = layoutInflate.inflate(R.layout.dialog_custom, null);
                AlertDialog customDialog = new AlertDialog.Builder(this)
                        .setTitle("标题")
                        .setIcon(R.mipmap.ic_launcher)
                        .setView(customView)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                customDialog.show();
                break;
            case R.id.btn_dialog_theme:
                Intent intent = new Intent(this, DialogThemeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("key","timmy");

                intent.putExtra("key",bundle);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //当Activity因内存底被系统回收或者其他异常问题导致Activity销毁前,
        // 会调用该方法,可以在方法的参数Bundle中保存我们的临时数据
        //再次回到这个界面时,可以再onCreate方法中获取到该数据
        outState.putString("name","timmy");
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

}
