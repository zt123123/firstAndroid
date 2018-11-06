package com.nodeveloper.myapplication.utils;

import android.util.Log;

/**
 * 日志工具函数
 */

public class L {
    private static final boolean DEBUG = true;
    private static final String TAG = "SmartButler";

    //debug
    public static void d(String text) {
        if (DEBUG) {
            Log.d(TAG, text);
        }
    }

    //info
    public static void i(String text) {
        if (DEBUG) {
            Log.i(TAG, text);
        }
    }

    //warning
    public static void w(String text) {
        if (DEBUG) {
            Log.w(TAG, text);
        }
    }

    //error
    public static void e(String text) {
        if (DEBUG) {
            Log.e(TAG, text);
        }
    }
}
