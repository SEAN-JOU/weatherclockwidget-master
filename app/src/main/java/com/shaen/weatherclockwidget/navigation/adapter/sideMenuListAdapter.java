package com.shaen.weatherclockwidget.navigation.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaen.weatherclockwidget.navigation.ImageInfo;
import com.shaen.weatherclockwidget.R;

import java.util.ArrayList;


public class sideMenuListAdapter extends BaseAdapter {



    private ArrayList<ImageInfo> mAryMenu = new ArrayList<>();
    private Context mContext;

    public sideMenuListAdapter(Context context)
    {
        mContext = context;
    }

    public void setData(ArrayList<ImageInfo> ary)
    {
        mAryMenu = ary;
    }

    @Override
    public int getCount() {
        return mAryMenu.size();
    }

    @Override
    public Object getItem(int position) {
        return mAryMenu.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        LayoutInflater mInflater = (LayoutInflater) mContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        convertView = mInflater.inflate(R.layout.view_side_menu_cell, parent, false);


        ((ImageView)convertView.findViewById(R.id.img_menuicon)).setImageResource(mAryMenu.get(position).getImageResource());
        ((TextView)convertView.findViewById(R.id.txt_menutext)).setText(mAryMenu.get(position).getTextString());


        return convertView;
    }

    private int getResID(int position)
    {
        int res = -1;

        switch (position)
        {
            case 0:
                res = R.drawable.love;
                break;
            case 1:
                res = R.drawable.love;
                break;
            case 2:
                res = R.drawable.love;
                break;
            case 3:
                res = R.drawable.love;
                break;
            case 4:
                res = R.drawable.love;
                break;
            case 5:
                res = R.drawable.love;
                break;
            case 6:
                res = R.drawable.love;
                break;
            case 7:
                res = R.drawable.love;
                break;
            case 8:
                res = R.drawable.love;
                break;

        }
        return res;}}
