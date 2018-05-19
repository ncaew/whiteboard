package com.chosen.whiteboard;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FloatWindowManager {

    /**
     * 小悬浮窗View的实例
     */
    private static FloatWindowHoverView windowHover;

    /**
     * 大悬浮窗View的实例
     */
    private static FloatWindowPopupView windowPopup;

    /**
     * 小悬浮窗View的参数
     */
    private static LayoutParams windowParamsHover;

    /**
     * 大悬浮窗View的参数
     */
    private static LayoutParams windowParamsPopup;

    /**
     * 用于控制在屏幕上添加或移除悬浮窗
     */
    private static WindowManager mWindowManager;

    /**
     * 用于获取手机可用内存
     */
    private static ActivityManager mActivityManager;

    /**
     * 创建一个小悬浮窗。初始位置为屏幕的右部中间位置。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createSmallWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (windowHover == null) {
            windowHover = new FloatWindowHoverView(context);
            if (windowParamsHover == null) {
                windowParamsHover = new LayoutParams();
                windowParamsHover.type = LayoutParams.TYPE_PHONE;
                windowParamsHover.format = PixelFormat.RGBA_8888;
                windowParamsHover.flags = LayoutParams.FLAG_NOT_TOUCH_MODAL
                        | LayoutParams.FLAG_NOT_FOCUSABLE;
                windowParamsHover.gravity = Gravity.START | Gravity.TOP;
                windowParamsHover.width = FloatWindowHoverView.viewWidth;
                windowParamsHover.height = FloatWindowHoverView.viewHeight;
                windowParamsHover.x = screenWidth;
                windowParamsHover.y = screenHeight / 2;
            }
            windowHover.setParams(windowParamsHover);
            windowManager.addView(windowHover, windowParamsHover);
        }
    }

    /**
     * 将小悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeSmallWindow(Context context) {
        if (windowHover != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(windowHover);
            windowHover = null;
        }
    }

    /**
     * 创建一个大悬浮窗。位置为屏幕正中间。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void createBigWindow(Context context) {
        WindowManager windowManager = getWindowManager(context);
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        if (windowPopup == null) {
            windowPopup = new FloatWindowPopupView(context);
            if (windowParamsPopup == null) {
                windowParamsPopup = new LayoutParams();
                windowParamsPopup.x = screenWidth / 2 - FloatWindowPopupView.viewWidth / 2;
                windowParamsPopup.y = screenHeight / 2 - FloatWindowPopupView.viewHeight / 2;
                windowParamsPopup.type = LayoutParams.TYPE_PHONE;
                windowParamsPopup.format = PixelFormat.RGBA_8888;
                windowParamsPopup.gravity = Gravity.START | Gravity.TOP;
                windowParamsPopup.width = FloatWindowPopupView.viewWidth;
                windowParamsPopup.height = FloatWindowPopupView.viewHeight;
            }
            windowManager.addView(windowPopup, windowParamsPopup);
        }
    }

    /**
     * 将大悬浮窗从屏幕上移除。
     *
     * @param context 必须为应用程序的Context.
     */
    public static void removeBigWindow(Context context) {
        if (windowPopup != null) {
            WindowManager windowManager = getWindowManager(context);
            windowManager.removeView(windowPopup);
            windowPopup = null;
        }
    }

    /**
     * 更新小悬浮窗的TextView上的数据，显示内存使用的百分比。
     *
     * @param context 可传入应用程序上下文。
     */
    public static void updateUsedPercent(Context context) {
        if (windowHover != null) {
            TextView percentView = windowHover.findViewById(R.id.percent);
            percentView.setText(getUsedPercentValue(context));
        }
    }

    /**
     * 是否有悬浮窗(包括小悬浮窗和大悬浮窗)显示在屏幕上。
     *
     * @return 有悬浮窗显示在桌面上返回true，没有的话返回false。
     */
    public static boolean isWindowShowing() {
        return windowHover != null || windowPopup != null;
    }

    /**
     * 如果WindowManager还未创建，则创建一个新的WindowManager返回。否则返回当前已创建的WindowManager。
     *
     * @param context 必须为应用程序的Context.
     * @return WindowManager的实例，用于控制在屏幕上添加或移除悬浮窗。
     */
    private static WindowManager getWindowManager(Context context) {
        if (mWindowManager == null) {
            mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        }
        return mWindowManager;
    }

    /**
     * 如果ActivityManager还未创建，则创建一个新的ActivityManager返回。否则返回当前已创建的ActivityManager。
     *
     * @param context 可传入应用程序上下文。
     * @return ActivityManager的实例，用于获取手机可用内存。
     */
    private static ActivityManager getActivityManager(Context context) {
        if (mActivityManager == null) {
            mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        }
        return mActivityManager;
    }

    /**
     * 计算已使用内存的百分比，并返回。
     *
     * @param context 可传入应用程序上下文。
     * @return 已使用内存的百分比，以字符串形式返回。
     */
    public static String getUsedPercentValue(Context context) {
        String dir = "/proc/meminfo";
        try {
            FileReader fr = new FileReader(dir);
            BufferedReader br = new BufferedReader(fr, 2048);
            String memoryLine = br.readLine();
            String subMemoryLine = memoryLine.substring(memoryLine.indexOf("MemTotal:"));
            br.close();
            long totalMemorySize = Integer.parseInt(subMemoryLine.replaceAll("\\D+", ""));
            long availableSize = getAvailableMemory(context) / 1024;
            int percent = (int) ((totalMemorySize - availableSize) / (float) totalMemorySize * 100);
            return percent + "%";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "悬浮窗";
    }

    /**
     * 获取当前可用内存，返回数据以字节为单位。
     *
     * @param context 可传入应用程序上下文。
     * @return 当前可用内存。
     */
    private static long getAvailableMemory(Context context) {
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        getActivityManager(context).getMemoryInfo(mi);
        return mi.availMem;
    }

}
