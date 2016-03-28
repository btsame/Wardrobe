package com.cy.yangbo.wardrobe.util;

import android.util.Log;

import com.cy.yangbo.wardrobe.comm.AppConfig;

public class LogUtils {

    public static void d(String tag, String msg) {
        if (AppConfig.DEBUG){
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (AppConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (AppConfig.DEBUG){
            Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable tr) {
        if (AppConfig.DEBUG) {
            Log.w(tag, msg, tr);
        }
    }

    public static void e(String tag, String msg) {
        if (AppConfig.DEBUG){
            Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable tr) {
        if (AppConfig.DEBUG) {
            Log.e(tag, msg, tr);
        }
    }
    
    public static void printException(Throwable e){
        if (AppConfig.DEBUG) {
//            MobclickAgent.reportError(BBTVApplication.getContext(), e);
            e.printStackTrace();
            return;
        }
    }
    
    
}
