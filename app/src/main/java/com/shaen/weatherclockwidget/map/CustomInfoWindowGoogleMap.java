package com.shaen.weatherclockwidget.map;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
import com.shaen.weatherclockwidget.R;

/**
 * Created by NB004 on 2018/7/3.
 */

public class CustomInfoWindowGoogleMap implements GoogleMap.InfoWindowAdapter {

    private Context context;

    public CustomInfoWindowGoogleMap(Context ctx){
        context = ctx;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = ((Activity)context).getLayoutInflater()
                .inflate(R.layout.marker, null);

        TextView name_tv = view.findViewById(R.id.name);
        TextView details_tv = view.findViewById(R.id.details);

        ImageView img = view.findViewById(R.id.pic);

        TextView hotel_tv = view.findViewById(R.id.hotels);
        TextView food_tv = view.findViewById(R.id.food);


        name_tv.setText(marker.getTitle());
        if(marker.getTitle() == null){
            name_tv.setVisibility(View.GONE);
        }


        details_tv.setText(marker.getSnippet());
        if(marker.getSnippet() == null){
            details_tv.setVisibility(View.GONE);
        }


        EatData infoWindowData = (EatData) marker.getTag();
//        Picasso.with(context).load(infoWindowData.getImages()).into(img);

        try {
            Glide.with(context)
                    .load(infoWindowData.getImages())
                    .into(img);
        }
        catch (Exception e){}

        if(infoWindowData.getImages() == null){
         img.setVisibility(View.GONE);
        }
//        hotel_tv.setText(infoWindowData.getTitle());
//        if(infoWindowData.getTitle() == null){
//            hotel_tv.setVisibility(View.GONE);
//        }
        food_tv.setText(infoWindowData.getDescription());
        if(infoWindowData.getDescription() == null){
            food_tv.setVisibility(View.GONE);
        }


        return view;
    }
}
