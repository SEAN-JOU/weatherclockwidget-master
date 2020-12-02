package com.shaen.weatherclockwidget.filedata.adapter;


import android.app.Activity;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.shaen.weatherclockwidget.R;

public class SpinnerAdapter extends BaseAdapter {

    TypedArray arrayimage;
    TypedArray Coffee_title;
    Activity aaa;
    int[]resource_id ={R.drawable.coffee_cappuccino,R.drawable.coffee_latte,R.drawable.coffee_mocha};

    public SpinnerAdapter(Activity aaa){
        this.aaa =aaa;
    }

    @Override
    public int getCount() {/*下拉式清單有幾個項目*/
        return 3;
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
        View v =aaa.getLayoutInflater().inflate(R.layout.layout,null);
        TextView txv =(TextView)v.findViewById(R.id.txv);
        ImageView imv =(ImageView)v.findViewById(R.id.imv);

        Coffee_title=aaa.getResources().obtainTypedArray(R.array.nba);
        arrayimage = aaa.getResources().obtainTypedArray(R.array.nba_logs);
        String[] avv =aaa.getResources().getStringArray(R.array.nba);

        txv.setText(avv[i]);
        imv.setImageDrawable(arrayimage.getDrawable(i));
        return v;
    }

    public TypedArray getCoffeetitle(){
        return Coffee_title;
    }
    public int[]resourceid()
    {return resource_id;

        /* */
    }}
