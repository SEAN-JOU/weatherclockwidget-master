package com.shaen.weatherclockwidget.main.notification.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import java.util.List;

/**
 * Created by wangxw on 2017/2/10 0009 15:05.
 * E-mail:wangxw725@163.com
 * function:
 */
public class MainAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;

    public MainAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;


    }

    @Override
    public int getCount() {
        return fragmentList!=null ? fragmentList.size() : 0;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

}
