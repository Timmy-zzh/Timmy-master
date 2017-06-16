package com.timmy.framework.imageLoaderFw.TimmyImageLoader.loader;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2017/6/14.
 * 加载管理器,根据传入的图片协议,决定使用哪种图片加载方式
 */
public class LoaderManager {

    public static LoaderManager instance;
    private Map<String, Loader> mLoaderMap = new HashMap<>();
    private static String HTTP = "http";
    private static String HTTPS = "https";
    private static String FILE = "file";

    private LoaderManager() {
        register(HTTP, new UrlLoader());
        register(HTTPS,new UrlLoader());
        register(FILE,new LocalLoader());
    }

    private void register(String schema, Loader loader) {
        mLoaderMap.put(schema,loader);
    }

    public static LoaderManager getInstance() {
        if (instance == null) {
            synchronized (LoaderManager.class) {
                if (instance == null) {
                    instance = new LoaderManager();
                }
            }
        }
        return instance;
    }

    public Loader getLoader(String schema) {
        return mLoaderMap.get(schema);
    }
}
