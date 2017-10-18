package com.timmy.framework.retrofit2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.timmy.R;
import com.timmy.library.util.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

/**
 * 探索Retrofit2.0网络框架实现原理:
 * 在我们使用retrofit框架进行网络请求时,我们不用像Volley框架一样将url和参数和请求方式全部用代码写出来,
 * 而是在一个方法上声明各种注解进行处理:(包括:请求头,请求类型,请求参数...)
 * <p>
 * 1.注解
 */
public class CustomRetrofit2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_retrofit2);

        Button button = (Button) findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testApi();
            }
        });
    }

    private void testApi() {
        IReqApi api = create(IReqApi.class);
        String timmy = api.login("timmy", "123456");
        Logger.d("result---:"+timmy);
    }

    private <T> T create(Class<T> service) {
        return (T) Proxy.newProxyInstance(
                service.getClassLoader(),
                new Class<?>[]{service},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        /**
                         * 拿到函数上的注解,再获取注解的数据
                         */
//                        Annotation[] annotations = method.getAnnotations();//拿到函数上的注解数组
                        //拿到方法上的请求类型注解
                        ReqType reqType = method.getAnnotation(ReqType.class);
                        Logger.d("reqType:"+reqType.reqType().toString());
                        //请求注解的url
                        ReqUrl reqUrl = method.getAnnotation(ReqUrl.class);
                        Logger.d("reqUrl:"+reqUrl.reqUrl());

                        //获取方法中的参数注解
//                        Type[] genericParameterTypes = method.getGenericParameterTypes();
                        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                        for (int i = 0; i < parameterAnnotations.length; i++) {
                            Annotation[] parameterAnnotation = parameterAnnotations[i];
                            if (parameterAnnotation != null){
                                ReqParam reqParams = (ReqParam) parameterAnnotation[0];
                                Logger.d("reqParams:"+reqParams.value() +",arg:"+args[i]);
                            }
                        }
                        return "result";
                    }
                }
        );
    }
}


























