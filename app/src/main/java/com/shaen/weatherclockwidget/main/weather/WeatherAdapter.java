package com.shaen.weatherclockwidget.main.weather;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaen.weatherclockwidget.R;

import java.util.List;

/**
 * Created by NB004 on 2018/6/29.
 */

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {


    Activity context;
    List<WeatherData.WeatherDataResults> results;

    public WeatherAdapter(List<WeatherData.WeatherDataResults> results, Activity context) {
        this.results = results;
        this.context = context;
    }


    @Override
    public WeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(),R.layout.item_weather, null);
        WeatherAdapter.ViewHolder holder = new ViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final WeatherData.WeatherDataResults result = results.get(position);


        String[] g1 = result.startTime.split("T");
        String[] describe2 = g1[0].split("-");
        String[] g2 = result.endTime.split("T");
        String[] describe3 = g2[0].split("-");

        holder.imv.setImageBitmap(WeatherFilter.weatherchange(context,result.parameterName1));
        holder.time.setText(changetimestring(g1[1]));
        holder.temperature.setText(result.parameterName3+"°C"+"/"+result.parameterName2+"°C");
        holder.day.setText(g1[0]);


    }


    @Override
    public long getItemId(int i) {/*取得列表中指定行相關聯的ID*/
        return i;
    }

    @Override
    public int getItemCount() {
        return results.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imv;
        TextView day;
        TextView time;
        TextView temperature;
        public ViewHolder(View itemView) {
            super(itemView);
            imv = itemView.findViewById(R.id.imv);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            temperature = itemView.findViewById(R.id.temperature);
        }}

    public String changetimestring(String s){
       if (s.equals(String.valueOf("18:00:00+08:00"))){
            return "下午";
        }
        else if (s.equals(String.valueOf("06:00:00+08:00"))){
            return "上午";
        }
        else {
           return "現在";
       }

    }

}
