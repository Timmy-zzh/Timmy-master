package com.timmy.framework.retrofit2;

/**
 * Created by admin on 2017/9/23.
 * 模拟Retrofit的各种Service存放请求的接口
 */
public interface IReqApi {

    /**
     * 用户登录请求
     * @return
     */
    @ReqType(reqType = ReqType.ReqTypeEnum.POST)
    @ReqUrl(reqUrl = "www.timmy.openApi/login")
    String login(@ReqParam("username") String name,@ReqParam("password") String password);
}
