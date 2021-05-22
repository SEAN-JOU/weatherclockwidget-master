package com.shaen.weatherclockwidget.camera;


import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.cameraview.CameraView;
import com.google.android.cameraview.callback.CameraControlListener;
import com.google.android.cameraview.callback.CameraVideoRecorderListener;
import com.google.android.cameraview.compress.inter.CompressListener;
import com.google.android.cameraview.configs.CameraViewOptions;
import com.shaen.weatherclockwidget.R;
import java.util.ArrayList;
import java.util.List;


public class CameraActivity extends AppCompatActivity implements CameraControlListener, CameraVideoRecorderListener, CompressListener,
        ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {

    private static final int IMAGE_REQUEST_CODE_PHOTO = 0x101;
    private String TAG = "CameraActivity";
    Button btnCamera, btnStop;
    ImageView ivFlash, btnChooice,btnPicture,gallery;
    CameraView mCameraView;
    private static final int PERMISSON_REQUESTCODE = 0;
    private boolean isNeedCheck = true;
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

    private int[] FLASH_ICONS = { R.drawable.ic_flash_off, R.drawable.ic_flash_on,R.drawable.ic_flash_auto};

    private int[] FLASH_OPTIONS = { CameraView.FLASH_OFF, CameraView.FLASH_ON,CameraView.FLASH_AUTO};

    private int[] Switch_Camera = {R.drawable.front, R.drawable.rear};

    private int currentFlash = 0;

    private int Switch_Camera_Number = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        gallery=findViewById(R.id.gallery);
        mCameraView = findViewById(R.id.mCameraView);
        btnCamera = findViewById(R.id.btnCamera);
        btnStop = findViewById(R.id.btnStop);
        btnChooice = findViewById(R.id.btnChooice);
        btnPicture = findViewById(R.id.btnPicture);
        ivFlash = findViewById(R.id.ivFlash);

        gallery.setOnClickListener(this);
        btnCamera.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnPicture.setOnClickListener(this);
        btnChooice.setOnClickListener(this);
        ivFlash.setOnClickListener(this);

        mCameraView.setControlListener(this);
        mCameraView.setRecorderListener(this);


        CameraViewOptions viewOptions = new CameraViewOptions.Builder(this).setCompressListener(this).create();
        mCameraView.setCameraOption(viewOptions);
        mCameraView.setFlash(FLASH_OPTIONS[currentFlash%3]);

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isNeedCheck) {
            checkPermissions(needPermissions);
        }

    }
    @Override
    public void onPause() {
        try {
            mCameraView.stopCamera();
            isNeedCheck=true;
        } catch (Exception e) {
            Log.e(TAG, "stopCamera camera fail");
        }
        super.onPause();
    }

//    public void onStop() {
//        super.onStop();
//        finish();
//    }


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


    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] paramArrayOfInt) {
        if (requestCode == PERMISSON_REQUESTCODE) {
            if (!verifyPermissions(paramArrayOfInt)) {
                isNeedCheck = false;
                try {
                    mCameraView.openCamera();
                } catch (Exception e) {
                }
            } else {
                try {
                    mCameraView.openCamera();
                } catch (Exception e) {
                }}}}


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void onStartVideoRecorder() {
        Log.e(TAG, "onStartVideoRecorder");
    }

    @Override
    public void onCompleteVideoRecorder() {
        Log.e(TAG, "onCompleteVideoRecorder ");
    }

    @Override
    public void onCameraOpened(CameraView cameraView) {
        Log.e(TAG, "onCameraOpened");
    }

    @Override
    public void onCameraClosed(CameraView cameraView) {
        Log.e(TAG, "onCameraClosed");
    }

    @Override
    public void onStartCompress() {
        Log.e(TAG, "onStartCompress");
    }

    @Override
    public void onCompressFail() {
        Log.e(TAG, "onCompressFail");
    }

    @Override
    public void onCompressSuccess(int action, String localPath, String compressPath) {
        Log.e(TAG, "onCompressSuccess: localPath:$localPath    compressPath:$compressPath");

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivFlash:
                currentFlash++;
                ivFlash.setImageResource(FLASH_ICONS[currentFlash % 3]);
                mCameraView.setFlash(FLASH_OPTIONS[currentFlash % 3]);
                break;
            case R.id.btnCamera:
                mCameraView.startVideoRecorder();
                Toast.makeText(CameraActivity.this,"Start Record",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnChooice:
                Switch_Camera_Number++;
                mCameraView.swithCamera();
                btnChooice.setImageResource(Switch_Camera[Switch_Camera_Number % 2]);
                break;
            case R.id.btnPicture:
                DisplayMetrics metrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(metrics);
                YoYo.with(Techniques.Landing)
                        .duration(1000).playOn(findViewById(R.id.mCameraView));
                YoYo.with(Techniques.DropOut)
                        .duration(1000).playOn(findViewById(R.id.btnPicture));
                mCameraView.takePicture();
                Toast t=  Toast.makeText(CameraActivity.this,"Save",Toast.LENGTH_SHORT);
                t.setGravity(Gravity.CENTER,0,0);
                t.show();
                break;
            case R.id.btnStop:
                mCameraView.stopVideoRecorder();
                Toast.makeText(CameraActivity.this,"Stop Record",Toast.LENGTH_SHORT).show();
                break;
            case R.id.gallery:
                Intent intentPhoto = new Intent();
                intentPhoto.addCategory(Intent.CATEGORY_OPENABLE);
                intentPhoto.setType("image/*");
                intentPhoto.setAction(Build.VERSION.SDK_INT < 19 ? Intent.ACTION_GET_CONTENT : Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(intentPhoto, IMAGE_REQUEST_CODE_PHOTO);
                break;
        }
    }
public void onDestroy(){
        super.onDestroy();
}
}

