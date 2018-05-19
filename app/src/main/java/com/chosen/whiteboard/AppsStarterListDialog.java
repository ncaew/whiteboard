package com.chosen.whiteboard;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class AppsStarterListDialog extends Dialog {
    private static String TAG = "Whiteboard";
    private Context context;
    private FullscreenActivity activity;

    public AppsStarterListDialog(Context context) {
        super(context);
        this.context = context;
    }

    public AppsStarterListDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    private class PInfo {
        private String appName = "";
        private String pkgName = "";
        private String className = "";
        private String versionName = "";
        private int versionCode = 0;
        private Drawable icon;
        private void prettyPrint() {
            Log.d(TAG, "App: " + appName + "  \t" + pkgName + "  \t" + className+ "  \t" + versionName+ "\t" + versionCode );
        }
    }

    private ArrayList<PInfo> getPackages() {
        ArrayList<PInfo> apps = getInstalledApps(); /* false = no system packages */
        final int max = apps.size();
        for (int i=0; i<max; i++) {
            apps.get(i).prettyPrint();
        }
        return apps;
    }

    private ArrayList<PInfo> getInstalledApps() {
        ArrayList<PInfo> res = new ArrayList<>();
        List<PackageInfo> packs = activity.getPackageManager().getInstalledPackages(0);
        for(int i=0;i<packs.size();i++) {
            PackageInfo p = packs.get(i);
            //if (p.versionName == null) {
            //    continue ;
            //}
            PInfo newInfo = new PInfo();
            newInfo.appName = p.applicationInfo.loadLabel(activity.getPackageManager()).toString();
            newInfo.pkgName = p.applicationInfo.packageName;
            newInfo.className = p.applicationInfo.className;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(activity.getPackageManager());
            res.add(newInfo);
        }
        return res;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.apps_starter_list_dialog);
        activity = (FullscreenActivity) getOwnerActivity();
        int width = 1920;
        int height = 1080;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            width = outMetrics.widthPixels;
            height = outMetrics.heightPixels;
        }
        Log.d(TAG, "AppsStarterListDialog onCreate: width = " + width + " height = " + height);
        GridLayout gridLayout = findViewById(R.id.gridLayout_apps_starter_list_dialog);
        int wButton = width / 10 * 8 / 7 - 10 ;
        int hButton = wButton + 1;
        int nCols =  width / 10 * 8 / wButton;
        gridLayout.setColumnCount(nCols);

        Window window = getWindow();
        if (window != null)
            window.setLayout(width / 10 * 8, height / 10 * 8);

        Button btnNew;
        String strTmp;
        Drawable drawable;
        ArrayList<PInfo> apps = getPackages();
        for ( int i = 0 ; i<apps.size(); i++) {
            btnNew = new Button(getContext());
            btnNew.setTag(i);
            btnNew.setTag(R.id.TAG_ID_pkgName,apps.get(i).pkgName);
            btnNew.setTag(R.id.TAG_ID_className,apps.get(i).className);
            strTmp = apps.get(i).appName;
            btnNew.setText(strTmp);
            btnNew.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            btnNew.setTextColor(Color.WHITE);
            btnNew.setGravity(Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL);
            drawable = apps.get(i).icon;
            //drawable.setBounds(0,0,wButton-10,hButton-10);
            //btnNew.setBackground(apps.get(i).icon);
            btnNew.setBackgroundDrawable(drawable);
            btnNew.setOnClickListener(btnClickListenerAppStarter);
            btnNew.setWidth(wButton);
            btnNew.setHeight(hButton);
            gridLayout.addView(btnNew);
        }
    }

    private View.OnClickListener btnClickListenerAppStarter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "btnClickListenerAppStarter: " + v.getTag());
            Log.d(TAG, "btnClickListenerAppStarter: " + v.getTag(R.id.TAG_ID_pkgName));
            Log.d(TAG, "btnClickListenerAppStarter: " + v.getTag(R.id.TAG_ID_className));

            Intent intent = new Intent("android.intent.action.MAIN");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            String strClassName;
            String strPkgName;
            strPkgName = v.getTag(R.id.TAG_ID_pkgName).toString();
            strClassName = v.getTag(R.id.TAG_ID_className).toString();
            intent.setClassName(strPkgName, strClassName);
            activity.startActivity(intent);
        }
    };


}
