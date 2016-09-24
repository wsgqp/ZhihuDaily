package com.demo.ian.zhihu.utils;

import com.orhanobut.logger.Logger;

/**
 * Created by Admin on 2016-9-8.
 */
public class LogUtil {

    public LogUtil() {
        Logger.init("GQP");
    }


    public static void e(String msg) {
        Logger.e(msg);
    }

    public static void v(String msg){
        Logger.v(msg);
    }
}
