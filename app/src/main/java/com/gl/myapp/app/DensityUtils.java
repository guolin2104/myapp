package com.gl.myapp.app;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;

/**
 * Created by zft on 2018/8/31.
 * 通过修改系统参数来适配android设备
 */

public class DensityUtils {

    private static float appDensity;
    private static float appScaleDensity;
    private static DisplayMetrics appDisplayMetrics;
    private static int barHeight;
    public final static String WIDTH = "width";
    public final static String HEIGHT = "height";

    /**
     * 在Application里初始化一下
     *
     * @param application
     */
    public static void setDensity(@NonNull final Application application) {
        //获取application的DisplayMetrics
        appDisplayMetrics = application.getResources().getDisplayMetrics();
        //获取状态栏高度
        barHeight = getStatusBarHeight(application);

        if (appDensity == 0) {
            //初始化的时候赋值
            appDensity = appDisplayMetrics.density;
            appScaleDensity = appDisplayMetrics.scaledDensity;

            //添加字体变化的监听
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    //字体改变后，将appScaleDensity重新赋值
                    if (newConfig != null && newConfig.fontScale > 0) {
                        appScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
    }

    /**
     * 此方法在BaseActivity中做初始化
     * 在setContentView()之前
     *
     * @param activity
     */
    public static void setDefault(Activity activity) {
        setAppOrientation(activity, WIDTH);
    }

    /**
     * 此方法用于在某一个Activity里面更改适配方向
     * 在setContentView()之前设置
     */
    public static void setOrientation(Activity activity, String orientation) {
        setAppOrientation(activity, orientation);
    }

    private static void setAppOrientation(Activity activity, String orientation) {
        float targetDensity;
        if (orientation.equals("height")) {
            targetDensity = (appDisplayMetrics.heightPixels - barHeight) / 667f;//设计图的高度，单位：dp
        } else {
            targetDensity = appDisplayMetrics.widthPixels / 360f;//设计图的宽度，单位：dp
        }
        float targetScaledDensity = targetDensity * (appScaleDensity / appDensity);
        int targetDensityDpi = (int) (160 * targetDensity);

        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        /**
         * 最后在这里将修改过后的值赋给系统参数
         * 只修改Activity的density值
         */
        DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * dp转px
     */
    public static int dp2px(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }

    /**
     * px转dp
     */
    public static int px2dp(Context context, int px) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    /**
     * px转sp
     */
    public static int px2sp(Context context, int px) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    /**
     * sp转px
     */
    public static int sp2px(Context context, int sp) {
        float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (sp * fontScale + 0.5f);
    }
}
