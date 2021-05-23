package com.shaen.weatherclockwidget.scan;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.shaen.weatherclockwidget.NumberUtils;
import com.shaen.weatherclockwidget.PrivacyServiceActivity;
import com.shaen.weatherclockwidget.R;
import com.shaen.weatherclockwidget.StringUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    EditText edt;
    private static final int PERMISSON_REQUESTCODE = 0;

    protected String[] needPermissions = {
            android.Manifest.permission.READ_CONTACTS,
            android.Manifest.permission.READ_SMS,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            android.Manifest.permission.CAMERA,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        checkPermissions(needPermissions);
        cameraView = (SurfaceView) findViewById(R.id.sfv);
        edt=(EditText) findViewById(R.id.edt);
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(
                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivity(intent);
            }
        });

        Button btn1 = findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("text", edt.getText().toString());
                manager.setPrimaryClip(clipData);
                Toast.makeText(ScanActivity.this,"複製成功",Toast.LENGTH_LONG).show();

            }
        });
    }

    protected void onResume(){
        super.onResume();
        createCameraSource();
    }

    private void createCameraSource() {
        BarcodeDetector barcodedetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE).build();
        final CameraSource camerasource = new CameraSource.Builder(this, barcodedetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1000,1000)
                .build();

        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(ScanActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                try {
                    camerasource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }
            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                camerasource.stop();
            }});

        barcodedetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }
            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcodes =detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    edt.post(new Runnable() {
                        public void run() {
                            edt.setText(barcodes.valueAt(0).displayValue);
                            try{
                                String inputString = edt.getText().toString();
                                String[] stringArray = inputString.split("\n");
                                if(edt.getText().toString().contains("http")){
                                    if(edt.getText().toString().contains("app")){
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(edt.getText().toString())));
                                        camerasource.stop();
                                    }else{
                                        Intent it = new Intent(ScanActivity.this, PrivacyServiceActivity.class);
                                        it.putExtra("URL1",edt.getText().toString());
                                        if(it.getStringExtra("URL1") != null){
                                            startActivity(it);
                                        }
                                    }
                                }else if(NumberUtils.isNumber(stringArray[0])){
                                    Uri uri = Uri.parse("smsto:" + stringArray[0]);
                                    Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
                                    intent.putExtra("sms_body", StringUtil.noFirstString(stringArray));
                                    startActivity(intent);
                                    camerasource.stop();
                                }
                            }catch (Exception e){
                                Toast.makeText(ScanActivity.this,"無法有效判別",Toast.LENGTH_LONG).show();
                            }
                        }});}}});}

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





