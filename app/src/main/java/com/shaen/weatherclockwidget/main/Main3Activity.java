package com.shaen.weatherclockwidget.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.chart.ChartActivity;
import com.shaen.weatherclockwidget.chatroom.ChatActivity;
import com.shaen.weatherclockwidget.cropview.CropActivity;
import com.shaen.weatherclockwidget.filedata.FileActivity;
import com.shaen.weatherclockwidget.gif.GifActivity;
import com.shaen.weatherclockwidget.rotateanimation.RotateAnimationActivity;
import com.shaen.weatherclockwidget.scan.ScanActivity;
import com.shaen.weatherclockwidget.video.VideoActivity;


import static com.shaen.weatherclockwidget.Constant.DOWNLOAD_APP_URL;


public class Main3Activity extends AppCompatActivity {

    Button video, chat, crop, rotate, chart, save, scan, gif;
    int cloudVersion = 0, localVersion = 0;
    String res;
    String STORE_PATH = Environment.getExternalStorageDirectory() + "/Download" + "/ScanQRcodeApp.apk";
    TextView progress;
    LinearLayout ggg;
    FirebaseDatabase database;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        progress = findViewById(R.id.aaaa);
        ggg = findViewById(R.id.ggg);
        video = findViewById(R.id.video);
        scan = findViewById(R.id.scan);
        chat = findViewById(R.id.chat);
        crop = findViewById(R.id.crop);
        rotate = findViewById(R.id.rotate);
        save = findViewById(R.id.save);
        chart = findViewById(R.id.chart);
        gif = findViewById(R.id.gif);

        scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, ScanActivity.class));
            }
        });
        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, VideoActivity.class));
            }
        });
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, ChatActivity.class));
            }
        });
        crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, CropActivity.class));
            }
        });
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, RotateAnimationActivity.class));
            }
        });
        chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, ChartActivity.class));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, FileActivity.class));
            }
        });

        gif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Main3Activity.this, GifActivity.class));
            }
        });
    }

    public void onResume() {
        super.onResume();
        initFirebase();
        updateVersion();
    }

    private void initFirebase() {
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Version");
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void startInstallPermissionSettingActivity() {
        //后面跟上包名，可以直接跳转到对应APP的未知来源权限设置界面。使用startActivityForResult 是为了在关闭设置界面之后，获取用户的操作结果，然后根据结果做其他处理
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES, Uri.parse("package:" + getPackageName()));
        startActivityForResult(intent, 1);
        Toast.makeText(Main3Activity.this, "請允許安裝後再返回", Toast.LENGTH_LONG).show();
    }

    Thread downloadApp = new Thread(new Runnable() {
        @Override
        public void run() {
            DownloadUtil.get().download(DOWNLOAD_APP_URL, "download", new DownloadUtil.OnDownloadListener() {
                @Override
                public void onDownloadSuccess() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Main3Activity.this, "下載完成", Toast.LENGTH_SHORT).show();
                            ggg.setVisibility(View.GONE);
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            if (Build.VERSION.SDK_INT >= 24) { //适配安卓7.0
                                StrictMode
                                        .setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                                                .detectDiskReads()
                                                .detectDiskWrites()
                                                .detectNetwork()   // or .detectAll() for all detectable problems
                                                .penaltyLog()
                                                .build());
                                StrictMode
                                        .setVmPolicy(new StrictMode.VmPolicy.Builder()
                                                .detectLeakedSqlLiteObjects()
                                                .detectLeakedClosableObjects()
                                                .penaltyLog()
                                                .penaltyDeath()
                                                .build());
                                i.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setAction(Intent.ACTION_VIEW);
                                i.setDataAndType(Uri.parse("file://" + STORE_PATH),
                                        "application/vnd.android.package-archive");

                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    boolean hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                                    if (!hasInstallPermission) {
                                        startInstallPermissionSettingActivity();
                                        return;
                                    }
                                }
                            } else {
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.setAction(Intent.ACTION_VIEW);
                                i.setDataAndType(Uri.parse("file://" + STORE_PATH),
                                        "application/vnd.android.package-archive");
                            }
                            startActivity(i);
                        }
                    });
                }
                @Override
                public void onDownloading(int aa) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            progress.setText(String.valueOf(aa) + "%");
                            ggg.setVisibility(View.VISIBLE);
                        }
                    });
                }
                @Override
                public void onDownloadFailed() {

                }
            });
        }
    });

    public void updateVersion() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String v = dataSnapshot.getValue(String.class);
                cloudVersion = Integer.valueOf(v);
                try {
                    PackageInfo pInfo = Main3Activity.this.getPackageManager().getPackageInfo(getPackageName(), 0);
                    localVersion = pInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
                if (localVersion != 0 && cloudVersion != 0) {
                    if (cloudVersion > localVersion) {
                        try {
                            new AlertDialog.Builder(Main3Activity.this)
                                    .setTitle("此版本必須強制更新")
                                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            downloadApp.start();
                                        }
                                    }).show();
                        } catch (Exception e) {
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.setAction(Intent.ACTION_VIEW);
                i.setDataAndType(Uri.parse("file://" + STORE_PATH),
                        "application/vnd.android.package-archive");// File.toString()会返回路径信息
                startActivity(i);
            }
        } else {
            if (requestCode == 1) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Main3Activity.this, "尚未允许安装,請到設定允許安裝", Toast.LENGTH_LONG).show();
                        }
                    });

                }
            } else {

            }
        }
    }
}
