package com.timmy.framework.annotationsFramework;

import android.app.Activity;

import com.timmy.framework.annotationsFramework.annotations.ContentView;
import com.timmy.framework.annotationsFramework.annotations.ViewInject;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by admin on 2017/3/30.
 * 注解使用工具类
 */
public class ViewInjectUtils {

    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity){
        injectContentView(activity);
        injectViews(activity);
    }

    /**
     * 注入主布局文件==>查询类上是否有我们自定义的注解
     * 1.先判断该类是否有注解
     * 2.有的话就使用反射实现我们要写的代码
     * @param activity
     */
    private static void injectContentView(Activity activity){
        Class<? extends Activity> aClass = activity.getClass();
        //获取类上是否有ContentView注解
        ContentView annotation = aClass.getAnnotation(ContentView.class);
        if (annotation!= null){//注解存在
            int layoutId = annotation.value();//获取注解上定义的属性
            /**
             * 使用反射方法实现setContentView(layId) 方法
             */
            //根据方法名称反射拿到方法实例
            try {
                Method method = aClass.getMethod(METHOD_SET_CONTENTVIEW,int.class);
                method.setAccessible(true); //如果该method是private修饰的,需要setAccessible(true)方法才能访问  Accessible访问的意思
                //反射方式实现setContentView()方法
                method.invoke(activity,layoutId);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入Views
     * 1.先获取activity类上的所有成员变量
     * 2.判断成员变量是否设置了ViewInject注解
     * 3.设置了注解,则使用反射方式实现findViewById方法
     * @param activity
     */
    public static void injectViews(Activity activity){
        Class<? extends Activity> aClass = activity.getClass();
        //获取所有成员变量->遍历
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            //判断该变量是否设置了ViewInject注解
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            int viewId = annotation.value();
            if (viewId !=  -1){
                /**
                 * 反射实现方法
                 * 先获取该类上的方法->反射实现
                 */
                try {
                    Method method = aClass.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
                    Object resView = method.invoke(activity, viewId);
                    field.setAccessible(true);
                    field.set(activity,resView);
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
