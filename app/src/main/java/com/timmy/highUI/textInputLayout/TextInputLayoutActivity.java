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

import java.util.regex.Pattern;

public class TextInputLayoutActivity extends BaseActivity {

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";

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

                if (!isMobile(phone)) {//输入的不是手机号
                    tilUserName.setError("请输入正确的手机号");
                    pass = false;
                }
                if (!isPassword(password)) {
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

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 11) {
                    tilUserName.setError("手机号最多11位");
                } else {
                    tilUserName.setErrorEnabled(false);
                }
            }
        });
    }

    private void doLogin() {
        Toast.makeText(TextInputLayoutActivity.this, "登录验证通过", android.widget.Toast.LENGTH_SHORT).show();
    }


    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }
}
