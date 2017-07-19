package com.timmy.framework.mvp.presenter;

import com.timmy.framework.mvp.bean.User;
import com.timmy.framework.mvp.model.ILoginModel;
import com.timmy.framework.mvp.model.LoginModelImpl;
import com.timmy.framework.mvp.view.ILoginView;
import com.timmy.library.util.Toast;

/**
 * Created by timmy1 on 17/7/16.
 * 登录业务逻辑处理的p层，持有v，和m的应用
 */
public class LoginPresenterImpl implements ILoginPresenter<User> {

    private final ILoginView mLoginView;
    private final ILoginModel mLoginModel;

    public LoginPresenterImpl(ILoginView loginView) {
        this.mLoginView = loginView;
        mLoginModel = new LoginModelImpl();
    }

    @Override
    public void login(User user) {
        mLoginView.logining();
        mLoginModel.login(user, new ILoginModel.OnLoginCallBack() {
            @Override
            public void onSuccess() {
                mLoginView.loginSuccess();
            }

            @Override
            public void onFail() {
                mLoginView.loginFail();
            }

            @Override
            public void onError(Throwable t) {
                Toast.toastShort("登录出错"+t.getMessage());
            }
        });
    }
}
