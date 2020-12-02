package com.shaen.weatherclockwidget.map;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.shaen.weatherclockwidget.R;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    AppBarLayout mAppBarLayout;//标题部分
    CollapsingToolbarLayout mCollapsingToolbarLayout;//折叠式标题栏
    Toolbar toolbar;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    String value;


    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appbar_layout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Map");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        //通过CollapsingToolbarLayout修改字体颜色
        mCollapsingToolbarLayout.setExpandedTitleColor(Color.BLACK);//设置还没收缩时状态下字体颜色
        mCollapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色


        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (mCollapsingToolbarLayout != null) {
            //设置隐藏图片时候ToolBar的颜色
            mCollapsingToolbarLayout.setContentScrimColor(Color.parseColor("#1E90FF"));
            //设置工具栏标题
            mCollapsingToolbarLayout.setTitle("");
        }
        //  mAppBarLayout.setExpanded(true);
        mAppBarLayout.setExpanded(false);

        mAppBarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, AppBarStateChangeListener.State state) {
                Log.d("STATE", state.name());
                if (state == State.EXPANDED) {

                    mCollapsingToolbarLayout.setTitle("");

                } else if (state == State.COLLAPSED) {

                    mCollapsingToolbarLayout.setTitle("好吃地圖");
                } else {

                }
            }
        });
    }

    public void onResume() {
        super.onResume();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                value = dataSnapshot.getValue(String.class);
                Log.d("aaaa", value);
                View view = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.layout_marker, null);
                String[] aaa = value.split(",&,");
                for (String a : aaa) {
                    EatData eatData = new Gson().fromJson(String.valueOf(a), EatData.class);
                    Log.d("aaaa", eatData.getTitle());
                    MarkerOptions markerOptions = new MarkerOptions();
                    String[] aaa1 = eatData.getPos().split(",");

                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                    view.setLayoutParams(new FrameLayout.LayoutParams(42, 56));
                    view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
                    view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
                    view.buildDrawingCache();
                    Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

                    Canvas canvas = new Canvas(bitmap);
                    view.draw(canvas);



                    markerOptions.position(new LatLng(Double.valueOf(aaa1[0]), Double.valueOf(aaa1[1])))
                            .title(eatData.getTitle())
//                            .snippet(eatData.getDescription())
//                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE));
                            .icon(BitmapDescriptorFactory.fromBitmap(bitmap));

                    CustomInfoWindowGoogleMap customInfoWindow = new CustomInfoWindowGoogleMap(MapsActivity.this);
                    mMap.setInfoWindowAdapter(customInfoWindow);
                    Marker m = mMap.addMarker(markerOptions);
                    m.setTag(eatData);

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(aaa1[0]), Double.valueOf(aaa1[1]))));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.setMinZoomPreference(11);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {

                mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(Double.valueOf(marker.getPosition().latitude+0.03), Double.valueOf(marker.getPosition().longitude))));
                marker.showInfoWindow();
                return true;
            }});
    }
}