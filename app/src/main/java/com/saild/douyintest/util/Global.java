package com.saild.douyintest.util;


import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;


/**
 * Created by linliangbin on 2016/10/10.
 */

public class Global {

    private static Handler handler;

    private static Context context;

    public static final String TAG = "Global_Videopaper";

    public static Context getContext() {
        return context;
    }

    public static Handler getHandler() {
        return handler;
    }

    public static void setContext(Context context) {
        Global.context = context;
    }

    public static void setHandler(Handler handler) {
        Global.handler = handler;
    }

    public static String getPkgName() {
        return context.getPackageName();
    }

    public static Context getApplicationContext() {
        return context.getApplicationContext();
    }

    public static Resources getResource() {
        return context.getResources();
    }

    /**
     * Description: 在主线程中执行的操作，避免重新创建对象
     * Author: linliangbin
     * Date: 2016/10/17 18:47
     */
    public static void runInMainThread(Runnable task) {
        if (task == null)
            return;
        if (handler != null) {
            handler.post(task);
        }
    }

    public static void runInMainThread(Runnable task, int delay) {
        if (task == null)
            return;
        if (handler != null) {
            handler.postDelayed(task, delay);
        }
    }

}
