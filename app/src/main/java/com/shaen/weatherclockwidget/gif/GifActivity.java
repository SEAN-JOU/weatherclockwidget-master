package com.shaen.weatherclockwidget.gif;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.shaen.weatherclockwidget.R;

public class GifActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gif);

        CircularImageView circularImageView = (CircularImageView)findViewById(R.id.civ);
// Set Border
        circularImageView.setBorderColor(getResources().getColor(R.color.colorPrimaryDark));
        circularImageView.setBorderWidth(10);
// Add Shadow with default param
        circularImageView.addShadow();
// or with custom param
        circularImageView.setShadowRadius(15);
        circularImageView.setShadowColor(Color.RED);
        circularImageView.setBackgroundColor(Color.RED);
        circularImageView.setShadowGravity(CircularImageView.ShadowGravity.CENTER);

    }
}
