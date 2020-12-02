package com.shaen.weatherclockwidget.filedata.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.filedata.Coffee;
import com.shaen.weatherclockwidget.filedata.FileActivity;

/**
 * Created by student on 2017/10/2.
 */

public class ListAdapter extends BaseAdapter {

    private FileActivity mainActivity;

    public ListAdapter(FileActivity mainActivity){

        this.mainActivity = mainActivity;
    }

    @Override
    public int getCount() {
        return mainActivity.getMcoffeelist().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mainActivity.getLayoutInflater().inflate(R.layout.list_view,null);
        TextView txv = (TextView)v.findViewById(R.id.txv1);
        TextView txv1 =(TextView)v.findViewById(R.id.txv2);
        ImageView imv =(ImageView)v.findViewById(R.id.imv);

        Coffee coffee = mainActivity.getMcoffeelist().get(i);
        txv.setText("id"+":"+i);
        txv1.setText(coffee.getPrice());
        imv.setImageResource(coffee.getResource_id());

        return v;
    }}
