package com.chosen.whiteboard;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class FloatWindowPopupView extends LinearLayout {

    /**
     * 记录大悬浮窗的宽度
     */
    public static int viewWidth;

    /**
     * 记录大悬浮窗的高度
     */
    public static int viewHeight;

    public FloatWindowPopupView(final Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.float_window_popup, this);
        View view = findViewById(R.id.layout_popup);
        viewWidth = view.getLayoutParams().width;
        viewHeight = view.getLayoutParams().height;
        Button btnHome = findViewById(R.id.btn_popup_home);
        btnHome.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击Home
                Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
                mHomeIntent.addCategory(Intent.CATEGORY_HOME);
                mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                getContext().startActivity(mHomeIntent);
                FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.createSmallWindow(context);
            }
        });

        Button btnBack = findViewById(R.id.btn_popup_back);
        btnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击Back
                FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.createSmallWindow(context);
            }
        });

        Button btnMenu = findViewById(R.id.btn_popup_menu);
        btnMenu.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击Menu
                FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.createSmallWindow(context);
            }
        });

        Button btnClose = findViewById(R.id.btn_popup_close_hover);
        btnClose.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击关闭悬浮窗的时候，移除所有悬浮窗，并停止Service
                FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.removeSmallWindow(context);
                Intent intent = new Intent(getContext(), FloatWindowService.class);
                context.stopService(intent);
            }
        });

        Button btnReturn = findViewById(R.id.btn_popup_return);
        btnReturn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击返回的时候，移除大悬浮窗，创建小悬浮窗
                FloatWindowManager.removeBigWindow(context);
                FloatWindowManager.createSmallWindow(context);
            }
        });
    }
}
