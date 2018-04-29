package com.muabe.propose.util;

import android.util.Log;

/**
 * <br>捲土重來<br>
 *
 * @author 오재웅(JaeWoong-Oh)
 * @email markjmind@gmail.com
 */

public class Mlog {
    public static void d(Object clz, String msg){
        Log.d(clz.getClass().getName(), msg);
    }

    public static void e(Object clz, String msg){
        Log.e(clz.getClass().getName(), msg);
    }

    public static void i(Object clz, String msg){
        Log.i(clz.getClass().getName(), msg);
    }

    public static void w(Object clz, String msg){
        Log.w(clz.getClass().getName(), msg);
    }
}
