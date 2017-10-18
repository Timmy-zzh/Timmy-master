package com.timmy.framework.annotationRuntime;

import android.app.Activity;
import android.view.View;

import com.timmy.framework.annotationRuntime.annotations.ContentView;
import com.timmy.framework.annotationRuntime.annotations.EventBase;
import com.timmy.framework.annotationRuntime.annotations.ViewInject;
import com.timmy.library.util.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by admin on 2017/3/30.
 * 注解使用工具类
 */
public class ViewInjectUtils {

    private static final String METHOD_SET_CONTENTVIEW = "setContentView";
    private static final String METHOD_FIND_VIEW_BY_ID = "findViewById";

    public static void inject(Activity activity) {
        injectContentView(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    /**
     * 注入主布局文件==>查询类上是否有我们自定义的注解
     * 1.先判断该类是否有注解
     * 2.有的话就使用反射实现我们要写的代码
     */
    private static void injectContentView(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取类上是否有ContentView注解
        ContentView annotation = aClass.getAnnotation(ContentView.class);
        if (annotation != null) {//注解存在
            int layoutId = annotation.value();//获取注解上定义的属性
            /**
             * 使用反射方法实现setContentView(layId) 方法
             */
            //根据方法名称反射拿到方法实例
            try {
                Method method = aClass.getMethod(METHOD_SET_CONTENTVIEW, int.class);
                method.setAccessible(true); //如果该method是private修饰的,需要setAccessible(true)方法才能访问  Accessible访问的意思
                //反射方式实现setContentView()方法
                method.invoke(activity, layoutId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 注入Views
     * 1.先获取activity类上的所有成员变量
     * 2.判断成员变量是否设置了ViewInject注解
     * 3.设置了注解,则使用反射方式实现findViewById方法
     *
     * @param activity
     */
    private static void injectViews(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        //获取所有成员变量->遍历
        Field[] fields = aClass.getDeclaredFields();
        for (Field field : fields) {
            //判断该变量是否设置了ViewInject注解
            ViewInject annotation = field.getAnnotation(ViewInject.class);
            if (annotation != null) {
                int viewId = annotation.value();
                if (viewId != -1) {
                    /**
                     * 反射实现方法
                     * 先获取该类上的方法->反射实现
                     */
                    try {
                        Method method = aClass.getMethod(METHOD_FIND_VIEW_BY_ID, int.class);
                        Object resView = method.invoke(activity, viewId);
                        field.setAccessible(true);
                        field.set(activity, resView);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * view的点击事件注入
     * 1.根据activity实例获取Activity类的Class实例
     * 2.拿到该class实例所有方法,遍历->拿到单个方法的所有注解->遍历
     * 3.拿到方法注解上的注解->EventBase
     * 4.拿到EventBase注解设置的参数
     * 5.拿到OnViewClick注解的参数-数组[]-遍历view的id
     * 6.根据id获取到该View对象,然后再反射实现设置view的点击事件
     */
    private static void injectEvents(Activity activity) {
        Class<? extends Activity> aClass = activity.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (Method method : declaredMethods) {
            //拿到方法的所有注解
            Annotation[] annotations = method.getAnnotations();

            for (Annotation annotation : annotations) {
                //获取到注解的类型
                Class<? extends Annotation> annotationType = annotation.annotationType();
                //获取方法注解的注解
                EventBase eventBase = annotationType.getAnnotation(EventBase.class);
                if (eventBase != null) {
                    //获取注解的参数
                    Class<?> listenerType = eventBase.listenerType();
                    String listenerSetter = eventBase.listenerSetter();
                    String methodName = eventBase.methodName();

                    try {
                        //获取注解的参数
                        Method declaredMethod = annotationType.getDeclaredMethod("value");//拿到OnClick注解的value方法
                        //取出所有的viewIds->反射
                        /**
                         * Error:(121, 82) 警告: 最后一个参数使用了不准确的变量类型的 varargs 方法的非 varargs 调用;
                         对于 varargs 调用, 应使用 Object
                         对于非 varargs 调用, 应使用 Object[], 这样也可以抑制此警告
                         解决方法:
                         Method method  =  cls.getMethod( " hashCode " ,  new  Class[ 0 ]);  //  编译通过
                         Method method  =  cls.getMethod( " hashCode " ,  null );  //  编译失败

                         allMethod[i].invoke(dbInstance,  new  Object[]{});  //  编译通过
                         allMethod[i].invoke(dbInstance,  null );  //  编译失败
                         */
//                        int[] viewIds = (int[]) declaredMethod.invoke(annotation,null);
                        int[] viewIds = (int[]) declaredMethod.invoke(annotation, new Object[]{});

                        //通过InvocationHander设置代理
                        DynamicHandler handler = new DynamicHandler(activity);
                        handler.addMethod(methodName, method);
                        Object listener = Proxy.newProxyInstance(listenerType.getClassLoader(), new Class<?>[]{listenerType}, handler);

                        Logger.d("Anno", "listener:" + listener.toString());
                        //遍历viewId,反射为每个view设置监听
                        for (int viewId : viewIds) {
                            View view = activity.findViewById(viewId);
                            Method setEventListenerMethod = view.getClass().getMethod(listenerSetter, listenerType);
                            setEventListenerMethod.invoke(view, listener);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
