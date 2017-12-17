package com.example.hp.qrcodescanner;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.PermissionRequest;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.vision.barcode.Barcode;

public class MainActivity extends AppCompatActivity {
    //Declaration
    Switch swFlash;
    Button btnScan;
    TextView tvResult,tvyourResult;
    Boolean isFlashOn=false;

    android.hardware.Camera camera;
    android.hardware.Camera.Parameters parameters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 200);
        }

        swFlash = (Switch) findViewById(R.id.swFlash);
        tvyourResult = (TextView) findViewById(R.id.tvyourResult);
        btnScan = (Button) findViewById(R.id.btnScan);
        tvResult = (TextView) findViewById(R.id.tvResult);



        if(camera==null||camera.getParameters()!=null)
        {
            camera=android.hardware.Camera.open();
            parameters=camera.getParameters();
        }


        swFlash.setTextOff("Off");
        swFlash.setTextOn("On");

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ScanActivity.class);
                intent.putExtra("flash",isFlashOn);
                startActivityForResult(intent, 100);
            }
        });


        swFlash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if(!isFlashOn)
                    {
                        turnOnLed();
                    }
                } else {
                    if(isFlashOn)
                    {
                        turnOffLed();
                    }
                }
            }
        });

    }



    public void turnOnLed() {
        parameters = camera.getParameters();
        parameters.setFlashMode(parameters.FLASH_MODE_TORCH);
        camera.setParameters(parameters);
        camera.startPreview();
        isFlashOn = true;
    }


    public  void  turnOffLed()
    {
        parameters=camera.getParameters();
        parameters.setFlashMode(parameters.FLASH_MODE_OFF);
        camera.setParameters(parameters);
        camera.startPreview();
        isFlashOn=false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        turnOnLed();

    }


    @Override
    protected void onPause() {
        super.onPause();
        turnOffLed();
    }

    @Override
    protected void onStop() {
        super.onStop();
        camera.release();
        camera=null;
    }

    public void onBackPressed()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this Application?");
        builder.setCancelable(false);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        builder.setNeutralButton("Canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==100&& resultCode==RESULT_OK)
        {
            if(data!=null)
            {
                final Barcode barcode =data.getParcelableExtra("barcode");
                tvResult.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(barcode.displayValue);
                    }
                });


            }
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("ans",tvResult.getText().toString());
        super.onSaveInstanceState(outState);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String a=savedInstanceState.getString("ans");
        tvResult.setText(a);
        super.onRestoreInstanceState(savedInstanceState);
    }
}//end of class
