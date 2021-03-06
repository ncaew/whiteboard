package com.chosen.whiteboard;

import android.os.Environment;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

class Config {
    private static String TAG = "CxtosLauncher";
    private static final String confDirectory = "CxtosLauncher";
    private static final String confFilename = "configure.json";
    private static final int confItemAmount = 6;
    private String dirConf;
    private String fileConf;

    class Starter {
        String pkgName;
        String className;
        String iconPath;
        String showName;
    }

    private ArrayList<Starter> listStarter = new ArrayList<>(confItemAmount);

    void initialize() {
        for (int i = 0; i < confItemAmount; i++) {
            listStarter.add(new Starter());
        }
        dirConf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
        dirConf += "/" + confDirectory;
        fileConf = dirConf + "/" + confFilename;
        Log.d(TAG, "Config initialize: " + fileConf);
        Log.w(TAG, "Config initialize: " + fileConf);
        Log.e(TAG, "Config initialize: " + fileConf);
    }

    String getPkgName(int i) {
        return listStarter.get(i).pkgName;
    }

    void setPkgName(int i, String v) {
        listStarter.get(i).pkgName = v;
    }

    String getClassName(int i) {
        return listStarter.get(i).className;
    }

    void setClassName(int i, String v) {
        listStarter.get(i).className = v;
    }

    String getIconPathname(int i) {
        return listStarter.get(i).iconPath;
    }

    void setIconPathname(int i, String v) {
        listStarter.get(i).iconPath = v;
    }

    String getShowName(int i) {
        return listStarter.get(i).showName;
    }

    void setShowName(int i, String v) {
        listStarter.get(i).showName = v;
    }

    void load() {
        int i;
        i = 0;
        listStarter.get(i).pkgName = "";
        listStarter.get(i).className = "";
        listStarter.get(i).iconPath = "";
        listStarter.get(i).showName = "";
        i = 1;
        listStarter.get(i).pkgName = "com.sct.educationpad";
        listStarter.get(i).className = "com.sct.educationpad";
        //listStarter.get(i).pkgName = "com.ckdz.whiteboard";
        //listStarter.get(i).className = "com.ckdz.whiteboard";
        listStarter.get(i).iconPath = "";
        listStarter.get(i).showName = "";
        i = 2;
        listStarter.get(i).pkgName = "com.suirui.zhumu";
        listStarter.get(i).className = "com.suirui.zhumu";
        listStarter.get(i).iconPath = "";
        listStarter.get(i).showName = "";
        i = 3;
        listStarter.get(i).pkgName = "com.android.documentsui";
        listStarter.get(i).className = "com.android.documentsui";
        //listStarter.get(i).pkgName = "com.chaozhuo.filemanager";
        //listStarter.get(i).className = "com.chaozhuo.filemanager";
        listStarter.get(i).iconPath = "";
        listStarter.get(i).showName = "";
        i = 4;
        listStarter.get(i).pkgName = "com.android.settings";
        listStarter.get(i).className = "com.android.settings";
        listStarter.get(i).iconPath = "";
        listStarter.get(i).showName = "";
        i = 5;
        listStarter.get(i).pkgName = "";
        listStarter.get(i).className = "";
        listStarter.get(i).iconPath = "";
        listStarter.get(i).showName = "";
        ///////////////////////////////
        String strConf = "";
        try {
            InputStream in = new FileInputStream(fileConf);
            int length = in.available();
            byte[] buffer = new byte[length];
            int nRead = in.read(buffer);
            if (length != nRead)
                Log.d(TAG, "Config load: Error on File");
            in.close();
            strConf = new String(buffer);
        } catch (Exception e) {
            Log.d(TAG, "load: Error" + e.toString());
            e.printStackTrace();
            save();
            Log.e(TAG, "load: SAVE file in: " + fileConf + "\n", e);
        }
        if (strConf.isEmpty()) {
            Log.d(TAG, "load: ");
        } else {
            try {
                JSONArray jsonArray = new JSONArray(strConf);
                //Log.d(TAG, "Config load: " + jsonArray.toString(4));
                JSONObject jsonObject;
                i = 0;
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("starter" + i + "_pkgName"))
                    listStarter.get(i).pkgName = jsonObject.getString("starter" + i + "_pkgName");
                else
                    listStarter.get(i).pkgName = "";
                if (jsonObject.has("starter" + i + "_className"))
                    listStarter.get(i).className = jsonObject.getString("starter" + i + "_className");
                else
                    listStarter.get(i).className = "";
                if (jsonObject.has("starter" + i + "_iconPath"))
                    listStarter.get(i).iconPath = jsonObject.getString("starter" + i + "_iconPath");
                else
                    listStarter.get(i).iconPath = "";
                if (jsonObject.has("starter" + i + "_showName"))
                    listStarter.get(i).showName = jsonObject.getString("starter" + i + "_showName");
                else
                    listStarter.get(i).showName = "";
                i = 1;
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("starter" + i + "_pkgName"))
                    listStarter.get(i).pkgName = jsonObject.getString("starter" + i + "_pkgName");
                else
                    listStarter.get(i).pkgName = "com.ckdz.whiteboard";
                if (jsonObject.has("starter" + i + "_className"))
                    listStarter.get(i).className = jsonObject.getString("starter" + i + "_className");
                else
                    listStarter.get(i).className = "com.ckdz.whiteboard";
                if (jsonObject.has("starter" + i + "_iconPath"))
                    listStarter.get(i).iconPath = jsonObject.getString("starter" + i + "_iconPath");
                else
                    listStarter.get(i).iconPath = "";
                if (jsonObject.has("starter" + i + "_showName"))
                    listStarter.get(i).showName = jsonObject.getString("starter" + i + "_showName");
                else
                    listStarter.get(i).showName = "";
                i = 2;
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("starter" + i + "_pkgName"))
                    listStarter.get(i).pkgName = jsonObject.getString("starter" + i + "_pkgName");
                else
                    listStarter.get(i).pkgName = "com.suirui.zhumu";
                if (jsonObject.has("starter" + i + "_className"))
                    listStarter.get(i).className = jsonObject.getString("starter" + i + "_className");
                else
                    listStarter.get(i).className = "com.suirui.zhumu";
                if (jsonObject.has("starter" + i + "_iconPath"))
                    listStarter.get(i).iconPath = jsonObject.getString("starter" + i + "_iconPath");
                else
                    listStarter.get(i).iconPath = "";
                if (jsonObject.has("starter" + i + "_showName"))
                    listStarter.get(i).showName = jsonObject.getString("starter" + i + "_showName");
                else
                    listStarter.get(i).showName = "";
                i = 3;
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("starter" + i + "_pkgName"))
                    listStarter.get(i).pkgName = jsonObject.getString("starter" + i + "_pkgName");
                else
                    listStarter.get(i).pkgName = "com.chaozhuo.filemanager";
                if (jsonObject.has("starter" + i + "_className"))
                    listStarter.get(i).className = jsonObject.getString("starter" + i + "_className");
                else
                    listStarter.get(i).className = "com.chaozhuo.filemanager";
                if (jsonObject.has("starter" + i + "_iconPath"))
                    listStarter.get(i).iconPath = jsonObject.getString("starter" + i + "_iconPath");
                else
                    listStarter.get(i).iconPath = "";
                if (jsonObject.has("starter" + i + "_showName"))
                    listStarter.get(i).showName = jsonObject.getString("starter" + i + "_showName");
                else
                    listStarter.get(i).showName = "";
                i = 4;
                jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.has("starter" + i + "_pkgName"))
                    listStarter.get(i).pkgName = jsonObject.getString("starter" + i + "_pkgName");
                else
                    listStarter.get(i).pkgName = "com.android.settings";
                if (jsonObject.has("starter" + i + "_className"))
                    listStarter.get(i).className = jsonObject.getString("starter" + i + "_className");
                else
                    listStarter.get(i).className = "com.android.settings";
                if (jsonObject.has("starter" + i + "_iconPath"))
                    listStarter.get(i).iconPath = jsonObject.getString("starter" + i + "_iconPath");
                else
                    listStarter.get(i).iconPath = "";
                if (jsonObject.has("starter" + i + "_showName"))
                    listStarter.get(i).showName = jsonObject.getString("starter" + i + "_showName");
                else
                    listStarter.get(i).showName = "";

            } catch (Exception e) {
                Log.d(TAG, "Config load: Error on Json" + e.toString());
                e.printStackTrace();
            }
        }
    } //load

    private void save() {
        JSONArray jsonArray = new JSONArray();
        Log.d(TAG, "Config save: a sample configure.json");
        try {
            for (int i = 0; i < confItemAmount; i++) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("starter" + i + "_pkgName", listStarter.get(i).pkgName);
                jsonObject.put("starter" + i + "_className", listStarter.get(i).className);
                jsonObject.put("starter" + i + "_iconPath", listStarter.get(i).iconPath);
                jsonObject.put("starter" + i + "_showName", listStarter.get(i).showName);
                jsonArray.put(jsonObject);
            }
        } catch (Exception e) {
            Log.d(TAG, "Config save: Error on Json");
            e.printStackTrace();
        }

        try {
            File f = new File(fileConf);
            File d = new File(dirConf);
            if (f.exists()) {
                if (f.delete())
                    Log.d(TAG, "Config save: Delete old config file done.");
            }
            try {
                if (d.mkdirs())
                    Log.d(TAG, "Config save: Create dir done.");
                if (f.createNewFile())
                    Log.d(TAG, "Config save: Create new config file done.");
            } catch (Exception e) {
                e.printStackTrace();
            }

            String strConf = jsonArray.toString(4);
            FileOutputStream fOut = new FileOutputStream(fileConf);
            byte[] bytes = strConf.getBytes();
            fOut.write(bytes);
            fOut.close();
        } catch (Exception e) {
            Log.d(TAG, "Config save: Error on File");
            e.printStackTrace();
        }
    }

}
