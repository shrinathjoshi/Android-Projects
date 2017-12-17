package com.example.hp.ledtorch;

        import android.content.pm.PackageManager;
        import android.graphics.SurfaceTexture;
        import android.hardware.Camera;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.Toast;

        import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnOn,btnOff;
    boolean hasCamera,isFlashOn;

    android.hardware.Camera camera;
    android.hardware.Camera.Parameters params;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOff= (Button) findViewById(R.id.btnOff);
        btnOn= (Button) findViewById(R.id.btnOn);

        PackageManager pm=getApplicationContext().getPackageManager();
        hasCamera=pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);

        if(!hasCamera)
        {
            Toast.makeText(getApplicationContext(), "NO Camera", Toast.LENGTH_SHORT).show();
            return;
        }


        btnOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFlashOn)
                {
                    turnOnFlash();
                }
            }
        });

        btnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isFlashOn)
                {
                    turnOffFlash();
                }
            }
        });


    }

    public void turnOnFlash()
    {
        params=camera.getParameters();
        params.setFlashMode(params.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();


        isFlashOn=true;
    }


    public void turnOffFlash()
    {
        params=camera.getParameters();
        params.setFlashMode(params.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashOn=false;
    }


    @Override
    protected void onStart() {
        super.onStart();

        if(camera==null)
        {
            camera=android.hardware.Camera.open();
            params=camera.getParameters();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        turnOnFlash();
    }


    @Override
    protected void onPause() {
        super.onPause();
        turnOffFlash();
    }


    @Override
    protected void onStop() {
        super.onStop();
        camera.release();
        camera=null;
    }
}


