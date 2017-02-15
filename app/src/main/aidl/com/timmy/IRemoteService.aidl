package com.timmy;

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    /**
     * 获取当前进程pid
     */
    int add(int a,int b);


    /**
      *获取当前服务名称
      */
      String getServiceName();


}
