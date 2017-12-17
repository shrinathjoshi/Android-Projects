package com.example.hp.qrcodescanner;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanActivity extends AppCompatActivity {

    SurfaceView cameraView;
    BarcodeDetector barcode;
    CameraSource cameraSource;
    SurfaceHolder holder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        cameraView= (SurfaceView) findViewById(R.id.cameraView);
        cameraView.setZOrderMediaOverlay(true);
        holder=cameraView.getHolder();

        barcode=new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE).build();

        if(!barcode.isOperational())
        {
            Toast.makeText(getApplicationContext(), "Sorry,Couldn't setup the Scanner", Toast.LENGTH_SHORT).show();
        }


        cameraSource=new CameraSource.Builder(this,barcode).setFacing(CameraSource.CAMERA_FACING_BACK).setRequestedFps(24).setAutoFocusEnabled(true).setRequestedPreviewSize(1920,1024).build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback(){

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                if (ContextCompat.checkSelfPermission(ScanActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    cameraSource.start(cameraView.getHolder());
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();;
            }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

            }
        });

        barcode.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {

                final SparseArray<Barcode> barcode=detections.getDetectedItems();
                if(barcode.size()>0)
                {
                    Intent intent=new Intent();
                    intent.putExtra("barcode",barcode.valueAt(0));
                    setResult(RESULT_OK,intent);
                    finish();
                }
            }
        });
    }



}
