package com.chosen.whiteboard;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

public class StarterListDialog extends Dialog {
    private static String TAG = "Whiteboard";
    private Context context;
    StarterListDialog(Context context) {
        super(context);
        this.context = context;
        Log.d(TAG, "StarterListDialog: Constructor method.");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setContentView(R.layout.starter_list_dialog);

        int width = 1920;
        int height = 1080;
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (manager != null) {
            DisplayMetrics outMetrics = new DisplayMetrics();
            manager.getDefaultDisplay().getMetrics(outMetrics);
            width = outMetrics.widthPixels;
            height = outMetrics.heightPixels;
        }
        Log.d(TAG, "StarterListDialog onCreate: width = " + width + " height = " + height);
        Window window = getWindow();
        if (window != null)
            window.setLayout(width / 10 * 9, height / 10 * 9);
            //window.setLayout(width , height);
    }

}
