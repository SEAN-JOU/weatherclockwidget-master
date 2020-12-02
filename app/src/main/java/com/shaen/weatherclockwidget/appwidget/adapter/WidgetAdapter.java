package com.shaen.weatherclockwidget.appwidget.adapter;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.appwidget.NewAppWidgetConfigureActivity;


/**
 * Created by NB004 on 2018/5/23.
 */

public class WidgetAdapter extends BaseAdapter {


    Activity context;
    int[] resources;
    int type;

    public WidgetAdapter(Activity context, int[] resources, int type) {
        this.context = context;
        this.resources = resources;
        this.type = type;
    }


    @Override
    public int getCount() {/*取得下拉式清單有幾個項目*/
        return resources.length;
    }

    @Override
    public Object getItem(int i) {/*取得與指定位置相關聯的數據*/
        return resources[i];
    }

    @Override
    public long getItemId(int i) {/*取得列表中指定行相關聯的ID*/
        return i;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = context.getLayoutInflater().inflate(R.layout.image_resource, null);
        /*從當前關聯的活動取得緩存的LayoutInflater用於片段的視圖 取得R.layout.layout*/


        if (type == NewAppWidgetConfigureActivity.BGCOLOR_TYPE) {
            ImageView imv = (ImageView) v.findViewById(R.id.imv);
            imv.setImageResource(resources[i]);
        } else if (type == NewAppWidgetConfigureActivity.STICK_TYPE) {
            ImageView imv = (ImageView) v.findViewById(R.id.imv);
            imv.setImageBitmap(BitmapFactory.decodeResource(context.getResources(),resources[i]));
        }


        return v;/*取得視圖顯示在指定位置*/
    }
}
