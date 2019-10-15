package com.docker.videobasic.util.liveroom;
import android.util.Log;

import com.docker.core.BuildConfig;

/**
 * Created by Akira on 2018/6/2.
 */

public class LogUtils {

    private static boolean isEnable = BuildConfig.DEBUG;

    public static void i(String tag, String content) {
        if (isEnable) {
            Log.i(tag, content);
        }
    }

    public static void d(String tag, String content) {
        if (isEnable) {
            Log.d(tag, content);
        }
    }

    public static void e(String tag, String content) {
        if (isEnable) {
            Log.e(tag, content);
        }
    }

    public static void w(String tag, String content) {
        if (isEnable) {
            Log.w(tag, content);
        }
    }
}
