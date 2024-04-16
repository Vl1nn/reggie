package com.ann.reggie.common;

/**
 * 基于ThreadLocal封装工具类，元数据处理对象 新增、更新操作时获取当前登录用户ID
 # 作用范围：某一个线程之内
 */
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();
    //设置值
    public static void setCurrentId(Long id){
        threadLocal.set(id);
    }
    // 获取值
    public static Long getCurrentId(){
        return threadLocal.get();
    }
}
