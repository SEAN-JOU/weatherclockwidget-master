package com.shaen.weatherclockwidget.main.information;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.shaen.weatherclockwidget.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InformationFragment extends Fragment implements View.OnClickListener {

    SuperButton seanstudiofb, seanstudioblog, nacocatfb, nacocatig, nacocatline;
    Activity context;

    public InformationFragment() {
    }

    @SuppressLint("ValidFragment")
    public InformationFragment(Activity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_information, container, false);

        nacocatfb = view.findViewById(R.id.nacocatfb);
        nacocatig = view.findViewById(R.id.nacocatig);
        seanstudioblog = view.findViewById(R.id.seanstudioblog);
        seanstudiofb = view.findViewById(R.id.seanstudiofb);
        nacocatline = view.findViewById(R.id.nacocatline);

        nacocatfb.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.use1));
        nacocatfb.setText("奈子貓FB粉專");
        nacocatig.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.use2));
        nacocatig.setText("奈子貓IG粉專");
        seanstudioblog.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.confidence));
        seanstudioblog.setText("奈子貓APP留言板");
        seanstudiofb.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.plus));
        seanstudiofb.setText("奈子貓APP粉專");
        nacocatline.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.run));
        nacocatline.setText("奈子貓Line貼圖");

        seanstudioblog.setOnClickListener(this);
        seanstudiofb.setOnClickListener(this);
        nacocatline.setOnClickListener(this);
        nacocatfb.setOnClickListener(this);
        nacocatig.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.seanstudioblog:

                Intent blog = new Intent(Intent.ACTION_VIEW);
                blog.setData(Uri.parse("http://shaenstudio.blogspot.tw/2018/05/app.html"));
                startActivity(blog);

                break;
            case R.id.seanstudiofb:

                Intent fb = new Intent(Intent.ACTION_VIEW);
                fb.setData(Uri.parse("https://www.facebook.com/ShaenStudio-2144980679076432"));
                startActivity(fb);

                break;
            case R.id.nacocatfb:

                Intent nacocatfb = new Intent(Intent.ACTION_VIEW);
                nacocatfb.setData(Uri.parse("https://m.facebook.com/蠢貓日常-1858759024176405/?ref=bookmarks"));
                startActivity(nacocatfb);

                break;

            case R.id.nacocatig:

                Intent nacocatig = new Intent(Intent.ACTION_VIEW);
                nacocatig.setData(Uri.parse("https://www.instagram.com/nakoneko_naco/"));
                startActivity(nacocatig);

                break;


            case R.id.nacocatline:

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://line.me/S/sticker/1485538"));
                startActivity(intent);

                break;
        }

    }
}
