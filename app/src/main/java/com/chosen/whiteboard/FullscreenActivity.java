package com.chosen.whiteboard;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class FullscreenActivity extends AppCompatActivity {
    private static String TAG = "CxtosLauncher";

    FrameLayout flFullscreen;
    ImageView ivWifi;
    TextView tvTime;
    ImageView ivBtn1;
    ImageView ivBtn2;
    ImageView ivBtn3;
    ImageView ivBtn4;
    ImageView ivBtn5;
    Config config = new Config();
    Button btnTest;
    StarterListDialog dlg;
    /*
    private WifiInfo wifiInfo = null;       //获得的Wifi信息
    private WifiManager wifiManager = null; //Wifi管理器
    private int level;                      //信号强度值
    private Handler handlerWifi = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                // 如果收到正确的消息就获取WifiInfo，改变图片并显示信号强度
                case 1:
                    ivWifi.setImageResource(R.drawable.wifi_full);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 信号最好", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_full " + level);
                    break;
                case 2:
                    ivWifi.setImageResource(R.drawable.wifi_best);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 信号较好", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_best " + level);
                    break;
                case 3:
                    ivWifi.setImageResource(R.drawable.wifi_better);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 信号一般", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_better " + level);
                    break;
                case 4:
                    ivWifi.setImageResource(R.drawable.wifi_weak);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 信号较差", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_weak " + level);
                    break;
                case 5:
                    ivWifi.setImageResource(R.drawable.wifi_bad);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 无信号", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_bad " + level);
                    break;
                case 6:
                    ivWifi.setImageResource(R.drawable.wifi_full); // wifi_unlink
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 无信号", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_unlink " + level);
                    break;
                default:
                    //以防万一
                    ivWifi.setImageResource(R.drawable.lan_link); // wifi_bad
                    //Toast.makeText(FullscreenActivity.this, "无信号", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_NOT_MATCH_ANY_KNOWN_STATUS " + level);
            }
            return true;
        }
    });
    */

    private Handler handlerNetworkConnection = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                // 如果收到正确的消息就获取handlerNetworkConnection，改变图片
                case 0:
                    ivWifi.setImageResource(R.drawable.lan_unlink);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 信号最好", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_full " + level);
                    break;
                case 1:
                    ivWifi.setImageResource(R.drawable.lan_link);
                    //Toast.makeText(FullscreenActivity.this, "信号强度：" + level + " 信号较好", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_best " + level);
                    break;
                default:
                    //以防万一
                    ivWifi.setImageResource(R.drawable.lan_unlink); // wifi_bad
                    //Toast.makeText(FullscreenActivity.this, "无信号", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG, "handleMessage: wifi_NOT_MATCH_ANY_KNOWN_STATUS " + level);
            }
            return true;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null)
            actionBar.hide();

        setContentView(R.layout.activity_fullscreen);

        flFullscreen = findViewById(R.id.frameLayout_fullscreen);
        ivWifi = findViewById(R.id.imageView_wifi);
        tvTime = findViewById(R.id.textView_time);
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


    } //onCreate

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        //
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                View mContentView = findViewById(R.id.constraintLayout_center);
                mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
                handler.postDelayed(this, 3000);
                //Log.d(TAG, "run: setSystemUiVisibility");
                Time t = new Time();
                t.setToNow();
                String strTime = String.format(Locale.GERMAN, "%02d:%02d", t.hour, t.minute);
                tvTime.setText(strTime);
                //有线网络连接状态直接改变
                ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                if (manager != null) {
                    NetworkInfo mNetworkInfo = manager.getActiveNetworkInfo();
                    if (mNetworkInfo != null && mNetworkInfo.isConnected()) {
                        //Log.d(TAG, "Network is active.");
                        Message msg = new Message();
                        msg.what = 1;
                        handlerNetworkConnection.sendMessage(msg);
                    } else {
                        //Log.d(TAG, "Network is not active.");
                        Message msg = new Message();
                        msg.what = 0;
                        handlerNetworkConnection.sendMessage(msg);
                    }
                } else {
                    Log.e(TAG, "getActiveNetworkInfo Error! ");
                    Message msg = new Message();
                    msg.what = 0;
                    handlerNetworkConnection.sendMessage(msg);
                }
                /*
                wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                if (wifiManager != null) {
                    wifiInfo = wifiManager.getConnectionInfo();
                    level = wifiInfo.getRssi(); //获得信号强度值
                    //根据获得的信号强度发送信息
                    if (level <= 0 && level >= -50) {
                        Message msg = new Message();
                        msg.what = 1;
                        handlerWifi.sendMessage(msg);
                    } else if (level < -50 && level >= -60) {
                        Message msg = new Message();
                        msg.what = 2;
                        handlerWifi.sendMessage(msg);
                    } else if (level < -60 && level >= -70) {
                        Message msg = new Message();
                        msg.what = 3;
                        handlerWifi.sendMessage(msg);
                    } else if (level < -70 && level >= -80) {
                        Message msg = new Message();
                        msg.what = 4;
                        handlerWifi.sendMessage(msg);
                    } else if (level < -80 && level >= -100) {
                        Message msg = new Message();
                        msg.what = 5;
                        handlerWifi.sendMessage(msg);
                    } else {
                        Message msg = new Message();
                        msg.what = 6;
                        handlerWifi.sendMessage(msg);
                    }
                }
                */
            }
        };
        handler.postDelayed(runnable, 1);//每两秒执行一次runnable.
        //handler.removeCallbacks(runnable); // 停止定时任务
        //
        ////Intent intent = new Intent(FullscreenActivity.this, FloatWindowService.class);
        ////startService(intent);
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
                BitmapDrawable d = new BitmapDrawable(getResources(), iconPathname);
                flFullscreen.setBackground(d);
            }

            iconPathname = config.getIconPathname(1);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                //File imageFile = new File(iconPathname);
                //ivBtn1.setImageURI(Uri.fromFile(imageFile));
                BitmapDrawable d = new BitmapDrawable(getResources(), iconPathname);
                ivBtn1.setBackground(d);
            }
            showName = config.getShowName(1);
            if (showName != null && !showName.isEmpty()) {
                //textView = findViewById(R.id.textView1);
                //textView.setText(showName);
                //Log.d(TAG, "onPostCreate: showName " + showName);
            }

            iconPathname = config.getIconPathname(2);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(getResources(), iconPathname);
                ivBtn2.setBackground(d);
            }
            showName = config.getShowName(2);
            if (showName != null && !showName.isEmpty()) {
                //textView = findViewById(R.id.textView2);
                //textView.setText(showName);
            }

            iconPathname = config.getIconPathname(3);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(getResources(), iconPathname);
                ivBtn3.setBackground(d);
            }
            showName = config.getShowName(3);
            if (showName != null && !showName.isEmpty()) {
                //textView = findViewById(R.id.textView3);
                //textView.setText(showName);
            }

            iconPathname = config.getIconPathname(4);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(getResources(), iconPathname);
                ivBtn4.setBackground(d);
            }
            showName = config.getShowName(4);
            if (showName != null && !showName.isEmpty()) {
                //textView = findViewById(R.id.textView4);
                //textView.setText(showName);
            }

            iconPathname = config.getIconPathname(5);
            if (iconPathname != null && !iconPathname.isEmpty()) {
                BitmapDrawable d = new BitmapDrawable(getResources(), iconPathname);
                ivBtn5.setBackground(d);
            }
            showName = config.getShowName(5);
            if (showName != null && !showName.isEmpty()) {
                //textView = findViewById(R.id.textView5);
                //textView.setText(showName);
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
