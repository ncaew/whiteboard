package com.chosen.whiteboard;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;

public class AppListAdapter extends ArrayAdapter<AppModel> {
    private static String TAG = "Whiteboard";
    private final LayoutInflater mInflater;

    AppListAdapter(Context context) {
        super(context, android.R.layout.simple_list_item_2);
        mInflater = LayoutInflater.from(context);
    }

    public void setData(ArrayList<AppModel> data) {
        clear();
        if (data != null) {
            addAll(data);
        }
    }

    @Override
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void addAll(@NonNull Collection<? extends AppModel> items) {
        //If the platform supports it, use addAll, otherwise add in loop
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            super.addAll(items);
        } else {
            for (AppModel item : items) {
                super.add(item);
            }
        }
    }

    @Override
    public @NonNull
    View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view;
        if (convertView == null) {
            view = mInflater.inflate(R.layout.starter_item, parent, false);
        } else {
            view = convertView;
        }
        AppModel item = getItem(position);
        if (item != null) {
            ((ImageView) view.findViewById(R.id.icon)).setImageDrawable(item.getIcon());
            ((TextView) view.findViewById(R.id.text)).setText(item.getLabel());
        } else {
            Log.d(TAG, "AppListAdapter getView: Should never goes here!");
        }
        return view;
    }

}
