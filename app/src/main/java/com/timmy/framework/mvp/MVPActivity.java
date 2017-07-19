package com.timmy.framework.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.timmy.R;
import com.timmy.framework.mvp.bean.User;
import com.timmy.framework.mvp.presenter.ILoginPresenter;
import com.timmy.framework.mvp.presenter.LoginPresenterImpl;
import com.timmy.framework.mvp.view.ILoginView;
import com.timmy.library.util.Toast;

/**
 * mvp设计模式代码实现：
 * 思路：
 * activity作为v层
 * 数据加载和处理作为m层
 * 中间通信枢纽p层
 * <p>
 * p持有v和m层的实际应用；每个activity都对应一个mvp组合，
 * 1.首先将v层需要执行的函数逻辑全部抽取出来制作成接口
 * 2.p层作为通信层主要作用就是就是让v层和m层的实例应用进行逻辑处理
 */
public class MVPActivity extends AppCompatActivity implements ILoginView, View.OnClickListener {

    private EditText mEtName;
    private EditText mEtPassword;
    private ILoginPresenter mLoginPresenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initView();
        mLoginPresenter = new LoginPresenterImpl(this);
    }

    private void initView() {
//        mEtName = (EditText) findViewById(R.id.et_name);
//        mEtPassword = (EditText) findViewById(R.id.et_password);
        TextInputLayout nameInputLayout = (TextInputLayout) findViewById(R.id.til_username);
        mEtName = nameInputLayout.getEditText();
        TextInputLayout passwordInputLayout = (TextInputLayout) findViewById(R.id.til_password);
        mEtPassword = passwordInputLayout.getEditText();

        Button mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnLogin.setOnClickListener(this);

        progressDialog = new ProgressDialog(this);
    }

    @Override
    public void logining() {
        progressDialog.setMessage("正在登录中");
        progressDialog.show();
//        ProgressDialog dialog = ProgressDialog.show(this, "", "正在登陆中");
    }

    @Override
    public void loginSuccess() {
        progressDialog.dismiss();
        Toast.toastShort("登录成功");
    }

    @Override
    public void loginFail() {
        progressDialog.dismiss();
        Toast.toastShort("登录失败");
    }

    @Override
    public void onClick(final View v) {
        final String name = mEtName.getText().toString().trim();
        final String password = mEtPassword.getText().toString().trim();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
            Toast.toastShort("用户名或密码不能为空");
            return;
        }
        hideKeyboard(v);
        //虚拟键盘隐藏后才开启登录
        mLoginPresenter.login(new User(name, password));
    }

    /**关闭虚拟键盘
     * @param view
     */
    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = getInputMethodManager(view);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private InputMethodManager getInputMethodManager(View view) {
        return (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
    }
}
