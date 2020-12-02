package com.shaen.weatherclockwidget.navigation;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.navigation.adapter.sideMenuListAdapter;
import java.util.ArrayList;


public class sideMenu extends LinearLayout
{

    private Context mContext 			    = null;
    private View m_View 				    = null;
    private sideMenuListAdapter mAdapter    = null;

    public sideMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        m_View = layoutInflater.inflate(R.layout.view_side_menu, this);

        initView();
    }

    private void initView()
    {
        ListView list = findViewById(R.id.list_side_menu);

        list.setDivider(null);
        list.setDividerHeight(0);

        mAdapter = new sideMenuListAdapter(mContext);

        ArrayList<ImageInfo> ary = new ArrayList<>();

        ary.add(new ImageInfo("貼圖製作",R.drawable.pencil));
        ary.add(new ImageInfo("好吃地圖",R.drawable.map));
        ary.add(new ImageInfo("照相機",R.drawable.camera));
//        ary.add(new ImageInfo("轉盤",R.drawable.rotate_icon));
//        ary.add(new ImageInfo("聊天室",R.drawable.chat));
//        ary.add(new ImageInfo("裁切",R.drawable.chat));
        ary.add(new ImageInfo("鬧鈴",R.drawable.alarm));
        ary.add(new ImageInfo("權限設定",R.drawable.setting));


        mAdapter.setData(ary);

        list.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

    }
}
