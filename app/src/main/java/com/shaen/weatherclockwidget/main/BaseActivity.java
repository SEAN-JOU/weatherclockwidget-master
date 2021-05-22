package com.shaen.weatherclockwidget.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.aivoice.AI1Activity;
import com.shaen.weatherclockwidget.other.CustomDialogFragment;
import com.shaen.weatherclockwidget.camera.CameraActivity;
import com.shaen.weatherclockwidget.map.MapsActivity;
import com.shaen.weatherclockwidget.navigation.ImageInfo;
import com.shaen.weatherclockwidget.scan.ScanActivity;
import com.shaen.weatherclockwidget.sticker.StickerActivity;
/**
 * Created by NB004 on 2018/6/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static boolean mSideMenuState=false;

    protected abstract int setContentView();

    protected abstract int setTitleBarView();

    protected abstract int setSideMenuView();

    protected abstract int setClosebuttonView();

    protected abstract int setCamerabuttonView();

    protected abstract int setAibuttonView();

    ImageView navigationbutton, closebutton,camerabutton,aibutton;
    protected DrawerLayout mSideMenu = null;
    DrawerLayout.DrawerListener mSideMenuEvent;
    TextView textView;


    AdapterView.OnItemClickListener mItemClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            switch (position) {
                case 0:
                    mSideMenuState=true;
                    startActivity(new Intent(BaseActivity.this, StickerActivity.class));
                    break;
                case 1:
                    mSideMenuState=true;
                    startActivity(new Intent(BaseActivity.this, MapsActivity.class));
                    break;
                case 2:
                    mSideMenuState=true;
                    startActivity(new Intent(BaseActivity.this, CameraActivity.class));
                    break;
                case 3:
                    mSideMenuState=true;
                    startActivity(new Intent(BaseActivity.this, AlarmActivity.class));
                    break;
                case 4:
                    mSideMenuState=true;
                    showMissingPermissionDialog();
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setContentView());

        textView=findViewById(R.id.textView);
        mSideMenu = findViewById(R.id.drawer_layout);
        mSideMenu.addDrawerListener(mSideMenuEvent);
        ((ListView) mSideMenu.findViewById(R.id.list_side_menu)).setOnItemClickListener(mItemClick);

        setToolBar();
        setSideMenu();
        setClosebutton();
        setCamerabutton();
        setAibutton();

        textView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                startActivity(new Intent(BaseActivity.this, Main3Activity.class));
                return false;
            }});

        SparseArray<ImageInfo> array = new SparseArray<>();
        array.put(1, new ImageInfo("a", R.drawable.close));

    }

    public void setClosebutton() {

        if (setClosebuttonView() != 0) {
            closebutton = findViewById(R.id.close);
            closebutton.setOnClickListener(mOnClickListener);
        }
    }

    public void setCamerabutton(){
        if (setCamerabuttonView() != 0) {
            camerabutton = findViewById(R.id.camera);
            camerabutton.setOnClickListener(mOnClickListener);
        }
    }

    public void setAibutton(){
        if (setAibuttonView() != 0) {
            aibutton = findViewById(R.id.ai);
            aibutton.setOnClickListener(mOnClickListener);
            aibutton.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    startActivity(new Intent(BaseActivity.this, AI1Activity.class));
                    return false;
                }
            });
        }
    }


    private void setToolBar() {
        if (setTitleBarView() != 0) {
            navigationbutton = (ImageView) findViewById(R.id.navigation);
            navigationbutton.setOnClickListener(mOnClickListener);

            mSideMenuEvent = new DrawerLayout.DrawerListener() {
                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                }

                @Override
                public void onDrawerOpened(View drawerView) {
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    mSideMenu.setVisibility(View.GONE);
                }

                @Override
                public void onDrawerStateChanged(int newState) {
                }
            };
        }
    }

    private void setSideMenu() {
        if (setSideMenuView() != 0) {
            mSideMenu = findViewById(R.id.drawer_layout);
            mSideMenu.addDrawerListener(mSideMenuEvent);
        }
    }


    public View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()) {
                case R.id.navigation:
                    mSideMenuState=true;
                    mSideMenu.setVisibility(View.VISIBLE);
                    mSideMenu.openDrawer(Gravity.START);

                    break;

                case R.id.ai:
                    startActivity(new Intent(BaseActivity.this, ScanActivity.class));
                    break;

                case R.id.close:

                    CustomDialogFragment fff = new CustomDialogFragment(BaseActivity.this);
                    fff.show(getSupportFragmentManager(), "A");//取得片段管理者以與此活動相關聯的片段連結

                    break;

                case R.id.camera:

                    startActivity(new Intent(BaseActivity.this, CameraActivity.class));

                    break;
            }
        }
    };

    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("權限設定");
        builder.setMessage("如未給予權限可能部分功能無法使用" +"\n"+
                "是否要進入權限設定?");

        // 拒绝, 退出应用
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builder.setPositiveButton("進入",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                });

        builder.setCancelable(false);
        builder.show();
    }


    private void startAppSettings() {
        Intent intent = new Intent(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}


