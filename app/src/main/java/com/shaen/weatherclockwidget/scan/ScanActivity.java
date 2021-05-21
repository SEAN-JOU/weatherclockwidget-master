package com.shaen.weatherclockwidget.scan;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.shaen.weatherclockwidget.NumberUtils;
import com.shaen.weatherclockwidget.PrivacyServiceActivity;
import com.shaen.weatherclockwidget.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    EditText ett;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        cameraView = (SurfaceView) findViewById(R.id.sfv);
        ett=(EditText)findViewById(R.id.ett);
        createCameraSource();
    }

    protected void onResume(){
        super.onResume();
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
                    ett.post(new Runnable() {
                        public void run() {
                            ett.setText(barcodes.valueAt(0).displayValue);
                            String inputString = ett.getText().toString();
                            String[] stringArray = inputString.split("\n");
                            Log.d("aaaaaaaa",stringArray[0]);
                            if(ett.getText().toString().contains("http")){
                                if(ett.getText().toString().contains("app")){
                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(ett.getText().toString())));
                                    camerasource.stop();
                                }else{
                                    Intent it = new Intent(ScanActivity.this, PrivacyServiceActivity.class);
                                    it.putExtra("URL1",ett.getText().toString());
                                    if(it.getStringExtra("URL1") != null){
                                        startActivity(it);
                                    }
                                }
                            }else if(NumberUtils.isNumber(stringArray[0])){
                                Uri uri = Uri.parse("smsto:" + stringArray[0]);
                                Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
                                intent.putExtra("sms_body",stringArray[1]);
                                startActivity(intent);
                                camerasource.stop();
                            }
                        }});}}});}}





