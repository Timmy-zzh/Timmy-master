package com.timmy.highUI.textInputLayout;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.timmy.R;
import com.timmy.base.BaseActivity;
import com.timmy.util.RegexUtil;

public class TextInputLayoutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_input_layout);
        initToolBar();

        final TextInputLayout tilUserName = (TextInputLayout) findViewById(R.id.til_username);
        final TextInputLayout tilPassword = (TextInputLayout) findViewById(R.id.til_password);
        Button btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = tilUserName.getEditText().getText().toString().trim();
                String password = tilPassword.getEditText().getText().toString().trim();
                boolean pass = true;
                tilUserName.setErrorEnabled(false);
                tilPassword.setErrorEnabled(false);

                if (!RegexUtil.isMobile(phone)) {//输入的不是手机号
                    tilUserName.setError("请输入正确的手机号");
                    pass = false;
                }
                if (!RegexUtil.isPassword(password)) {
                    tilPassword.setError("密码至少6位");
                    pass = false;
                }
                if (!pass) {
                    return;
                }
                doLogin();
            }
        });
        //设置提示信息
        tilUserName.setHint("手机号");
        tilUserName.setCounterEnabled(true);
        tilUserName.setCounterMaxLength(11);
        tilPassword.setHint("密码");

        tilUserName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 11) {
                    tilUserName.setError("手机号最多11位");
                } else {
                    tilUserName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void doLogin() {
        Toast.makeText(TextInputLayoutActivity.this, "登录验证通过", android.widget.Toast.LENGTH_SHORT).show();
    }
}
