package com.chosen.whiteboard;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    private static String TAG = "Whiteboard";

    FrameLayout flFullscreen;
    ImageView ivBtn1;
    ImageView ivBtn2;
    ImageView ivBtn3;
    ImageView ivBtn4;
    ImageView ivBtn5;
    Config config = new Config();
    Button btnTest;
    StarterListDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)
            actionBar.hide();

        flFullscreen = findViewById(R.id.frameLayout_fullscreen);
        ivBtn1 = findViewById(R.id.imageView1);
        ivBtn2 = findViewById(R.id.imageView2);
        ivBtn3 = findViewById(R.id.imageView3);
        ivBtn4 = findViewById(R.id.imageView4);
        ivBtn5 = findViewById(R.id.imageView5);
        ivBtn1.setOnClickListener(listenerImageViewBtnX);
        ivBtn2.setOnClickListener(listenerImageViewBtnX);
        ivBtn3.setOnClickListener(listenerImageViewBtnX);
        ivBtn4.setOnClickListener(listenerImageViewBtnX);
        ivBtn5.setOnClickListener(listenerImageViewBtnX);

        btnTest = findViewById(R.id.button_test);
        dlg = new StarterListDialog(this);
        dlg.setOwnerActivity(this);
        btnTest.setVisibility(View.GONE);
        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "button_test onClick: ");
                //final PackageManager pm = getPackageManager(); //get a list of installed apps.
                //List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
                //for (ApplicationInfo packageInfo : packages) {
                //    Log.d(TAG, "Installed package :" + packageInfo.packageName);
                //    Log.d(TAG, "Source dir : " + packageInfo.sourceDir);
                //    Log.d(TAG, "Launch Activity :" + pm.getLaunchIntentForPackage(packageInfo.packageName));
                //}
                Log.d(TAG, "Apps Information here: ===================================================");
                dlg.show();
            }
        });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        //delayedHide(100);
        //
        Intent intent = new Intent(FullscreenActivity.this, FloatWindowService.class);
        startService(intent);
        //
        config.initialize();
        config.load();
        //config.save();
        TextView textView;
        String showName;
        String iconPathname;
        try {
            iconPathname = config.getIconPathname(0);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(iconPathname);
                flFullscreen.setBackgroundDrawable(d);
            }

            iconPathname = config.getIconPathname(1);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                //File imageFile = new File(iconPathname);
                //ivBtn1.setImageURI(Uri.fromFile(imageFile));
                BitmapDrawable d = new BitmapDrawable(iconPathname);
                ivBtn1.setBackgroundDrawable(d);
            }
            showName = config.getShowName(1);
            if (showName != null && !showName.isEmpty()) {
                textView = findViewById(R.id.textView1);
                textView.setText(showName);
                //Log.d(TAG, "onPostCreate: showName " + showName);
            }

            iconPathname = config.getIconPathname(2);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(iconPathname);
                ivBtn2.setBackgroundDrawable(d);
            }
            showName = config.getShowName(2);
            if (showName != null && !showName.isEmpty()) {
                textView = findViewById(R.id.textView2);
                textView.setText(showName);
            }

            iconPathname = config.getIconPathname(3);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(iconPathname);
                ivBtn3.setBackgroundDrawable(d);
            }
            showName = config.getShowName(3);
            if (showName != null && !showName.isEmpty()) {
                textView = findViewById(R.id.textView3);
                textView.setText(showName);
            }

            iconPathname = config.getIconPathname(4);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(iconPathname);
                ivBtn4.setBackgroundDrawable(d);
            }
            showName = config.getShowName(4);
            if (showName != null && !showName.isEmpty()) {
                textView = findViewById(R.id.textView4);
                textView.setText(showName);
            }

            iconPathname = config.getIconPathname(5);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(iconPathname);
                ivBtn5.setBackgroundDrawable(d);
            }
            showName = config.getShowName(5);
            if (showName != null && !showName.isEmpty()) {
                textView = findViewById(R.id.textView5);
                textView.setText(showName);
            }

        } catch (Exception e) {
            Log.d(TAG, "FullscreenActivity onCreate: Error on doing replace image");
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "FullscreenActivity onBackPressed: Same as HOME Key");
        //实现Home键效果
        //super.onBackPressed();这句话一定要注掉,不然又去调用默认的back处理方式
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    View.OnClickListener listenerImageViewBtnX = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                int n;
                PackageManager pm = getPackageManager();
                Intent intent;
                if (v == ivBtn1) {
                    Log.d(TAG, "onClick: 1");
                    n = 1;
                } else if (v == ivBtn2) {
                    Log.d(TAG, "onClick: 2");
                    n = 2;
                } else if (v == ivBtn3) {
                    Log.d(TAG, "onClick: 3");
                    n = 3;
                } else if (v == ivBtn4) {
                    Log.d(TAG, "onClick: 4");
                    n = 4;
                } else if (v == ivBtn5) {
                    n = 5;
                } else {
                    n = 5;
                    Log.d(TAG, "listenerImageViewBtnX: Should never goes here!");
                }
                if (n < 5) {
                    String pkg = config.getPkgName(n);
                    Log.d(TAG, "going to start package : " + pkg);
                    intent = pm.getLaunchIntentForPackage(pkg);
                    if (intent != null) {
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        Log.d(TAG, "going to start package : " + intent.getPackage());
                        ComponentName ac = intent.resolveActivity(pm);
                        Log.d(TAG, "going to start activity: " + ac.getClassName());
                        startActivity(intent);
                    }
                } else {
                    dlg.show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

}
