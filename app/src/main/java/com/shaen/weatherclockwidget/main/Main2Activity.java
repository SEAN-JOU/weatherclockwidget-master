package com.shaen.weatherclockwidget.main;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.shaen.weatherclockwidget.main.notification.DataDetail;
import com.shaen.weatherclockwidget.other.ActivityController;
import com.shaen.weatherclockwidget.R;
import com.squareup.picasso.Picasso;

public class Main2Activity extends AppCompatActivity {


    DataDetail ddd;
    TextView title, content, web;
    WebView video;
    ImageView photo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Intent intent = getIntent();
        ddd = (DataDetail) intent.getSerializableExtra("hhh");

        ActivityController.main2Activity =this;

        title = findViewById(R.id.title);
        video = findViewById(R.id.video);
        web = findViewById(R.id.web);
        content = findViewById(R.id.content);
        photo = findViewById(R.id.photo);
    }


    @Override
    protected void onStart() {
        super.onStart();

        getSupportActionBar().setDisplayShowTitleEnabled(true);
        try{
        getSupportActionBar().setTitle(ddd.getTitle());}
        catch (Exception e){
            getSupportActionBar().setTitle(R.string.app_name);
        }

        if(!String.valueOf(ddd.getPhoto()).equals("null")){
            photo.setVisibility(View.VISIBLE);
            Picasso.with(this).load(Uri.parse(ddd.getPhoto())).into(photo);
        }

        if (!String.valueOf(ddd.getTitle()).equals("null")) {
            title.setVisibility(View.VISIBLE);
            title.setText(ddd.getTitle());
        }
        if (!String.valueOf(ddd.getContent()).equals("null")) {
            content.setVisibility(View.VISIBLE);
            content.setText(ddd.getContent());
        }
        if (!String.valueOf(ddd.getWeb()).equals("null")) {
            web.setVisibility(View.VISIBLE);
            web.setText(ddd.getWeb());
        }
        if (!String.valueOf(ddd.getVideo()).equals("null")) {
            video.setVisibility(View.VISIBLE);
            video.setWebChromeClient(new WebChromeClient() {
            });
            video.loadUrl(String.valueOf(ddd.getVideo()));
            WebSettings webSettings = video.getSettings();
            webSettings.setJavaScriptEnabled(true);
        }
    }

    public void onResume() {
        super.onResume();
        ActivityController.main2Activity = Main2Activity.this;
    }
}
