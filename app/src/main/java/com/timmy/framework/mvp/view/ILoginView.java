package com.timmy.framework.mvp.view;

/**
 * Created by timmy1 on 17/7/16.
 * 用户进行登录会执行的业务逻辑处理
 * 1.登录中－－》访问后台
 * 2.登录成功
 * 3.登录失败
 */
public interface ILoginView{

    void logining();

    void loginSuccess();

    void loginFail();

}
