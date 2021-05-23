package com.shaen.weatherclockwidget.main;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.shaen.weatherclockwidget.main.notification.adapter.MainAdapter;
import com.shaen.weatherclockwidget.main.notification.NotificationFragment;
import com.shaen.weatherclockwidget.other.CustomDialogFragment;
import com.shaen.weatherclockwidget.main.information.InformationFragment;
import com.shaen.weatherclockwidget.main.star.StarFragment;
import com.shaen.weatherclockwidget.main.weather.WeatherFragment;
import com.shaen.weatherclockwidget.other.ActivityController;
import com.shaen.weatherclockwidget.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    CircleImageView circleImageView;
    PrefManager1 prefManager1;
    private static final int PERMISSON_REQUESTCODE = 0;
    private InterstitialAd mInterstitialAd;
    AdView mAdView;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private static int sOffScreenLimit = 1;
    int cloudVersion = 0, localVersion = 0;
    FirebaseDatabase database;
    DatabaseReference databaseVersion, databaseUpdate;
    boolean isGooglePlay = true;
    protected String[] needPermissions = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.READ_PHONE_STATE,
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.WRITE_CONTACTS,
            android.Manifest.permission.RECORD_AUDIO,
            android.Manifest.permission.READ_PHONE_STATE,
    };


    @Override
    protected int setContentView() {
        return R.layout.activity_main;
    }

    @Override
    protected int setTitleBarView() {
        return 1;
    }

    @Override
    protected int setSideMenuView() {
        return 1;
    }

    @Override
    protected int setClosebuttonView() {
        return 1;
    }

    @Override
    protected int setCamerabuttonView() {
        return 1;
    }

    @Override
    protected int setAibuttonView() {
        return 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        circleImageView = findViewById(R.id.profile_image);
        Glide.with(this).load("http://present810209.twf.node.tw/uploads/image.png").into(circleImageView);

        database = FirebaseDatabase.getInstance();
        databaseVersion = database.getReference("Version");
        databaseUpdate = database.getReference("Update");

        ActivityController.mainActivity = MainActivity.this;
        MobileAds.initialize(this, getResources().getString(R.string.banner_ad_unit_id));
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getResources().getString(R.string.banner_ad_unit_id1));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);

        initData();
        View headerView = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                .inflate(R.layout.custom_tab, null, false);


        LinearLayout linearLayoutOne = (LinearLayout) headerView.findViewById(R.id.ll);
        LinearLayout linearLayout2 = (LinearLayout) headerView.findViewById(R.id.ll2);
        LinearLayout linearLayout3 = (LinearLayout) headerView.findViewById(R.id.ll3);
        LinearLayout linearLayout4 = (LinearLayout) headerView.findViewById(R.id.ll4);
        mTabLayout.getTabAt(0).setCustomView(linearLayoutOne);
        mTabLayout.getTabAt(1).setCustomView(linearLayout2);
        mTabLayout.getTabAt(2).setCustomView(linearLayout3);
        mTabLayout.getTabAt(3).setCustomView(linearLayout4);

        checkPermissions(needPermissions);
        checkVersion();
    }

    public void onResume() {
        super.onResume();
    }

    public void checkVersion() {
        databaseVersion.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        cloudVersion = Integer.valueOf(value);
                        try {
                            PackageInfo pInfo = MainActivity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                            localVersion = pInfo.versionCode;
                        } catch (PackageManager.NameNotFoundException e) {
                            e.printStackTrace();
                        } finally {
                            updateApp();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                }
        );
    }

    public void updateApp() {
        if (localVersion != 0 && cloudVersion != 0) {
            if (cloudVersion > localVersion) {
                databaseUpdate.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value = dataSnapshot.getValue(String.class);
                        isGooglePlay = Boolean.parseBoolean(value);
                        if (isGooglePlay) {
                            new AlertDialog.Builder(MainActivity.this).setTitle("版本更新")
                                    .setIcon(R.mipmap.ic_launcher).setMessage("前往GooglePlay更新奈子貓奔跑鐘")//設定顯示的文字
                                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .setPositiveButton("更新", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent googleplay = new Intent(Intent.ACTION_VIEW);
                                            googleplay.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.shaen.weatherclockwidget"));
                                            startActivity(googleplay);
                                        }
                                    }).show();
                        } else {
                            Intent it = new Intent(MainActivity.this, Main3Activity.class);
                            startActivity(it);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        }
    }

    private void initData() {
        ArrayList<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new NotificationFragment(this));
        fragmentList.add(new WeatherFragment(this));
        fragmentList.add(new InformationFragment(this));
        fragmentList.add(new StarFragment(this));

        ArrayList<String> titleList = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            titleList.add("Page_" + i);
        }

        MainAdapter adapter = new MainAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
        if (sOffScreenLimit > 1) {
            mViewPager.setOffscreenPageLimit(sOffScreenLimit);
            Log.i("wxw", "偏移量>1...:" + sOffScreenLimit);
        } else {
            Log.i("wxw", "偏移量...:" + sOffScreenLimit);
        }
        prefManager1 = new PrefManager1(MainActivity.this);


        if (prefManager1.isFirstTimeLaunch()) {
            launchHomeScreen();

        }
    }


    private void launchHomeScreen() {
        prefManager1.setFirstTimeLaunch(false);
        String token = FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("FCMid");
        myRef.child(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.TAIWAN)
                .format(new Date()))
                .setValue(token);
    }

    public void onBackPressed() {
        if (mSideMenuState) {
            mSideMenuState = false;
            mSideMenu.setVisibility(View.GONE);
        } else {
            CustomDialogFragment fff = new CustomDialogFragment(MainActivity.this);
            fff.show(getSupportFragmentManager(), "A");
        }
    }

    private void checkPermissions(String... permissions) {
        List<String> needRequestPermissonList = findDeniedPermissions(permissions);
        if (null != needRequestPermissonList
                && needRequestPermissonList.size() > 0) {
            ActivityCompat.requestPermissions(this,
                    needRequestPermissonList.toArray(
                            new String[needRequestPermissonList.size()]),
                    PERMISSON_REQUESTCODE);
        }
    }

    private List<String> findDeniedPermissions(String[] permissions) {
        List<String> needRequestPermissonList = new ArrayList<String>();
        for (String perm : permissions) {
            if (ContextCompat.checkSelfPermission(this,
                    perm) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.shouldShowRequestPermissionRationale(
                    this, perm)) {
                needRequestPermissonList.add(perm);
            }
        }
        return needRequestPermissonList;
    }
}

