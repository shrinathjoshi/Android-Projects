package com.example.hp.cleanindia;

import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener{

    ImageView ivPicture;
    Button btnTakePicture,btnSharePicture;
    TextView tvLocation;
    Bitmap photo;

    GoogleApiClient mLocationClient;
    Location mLastLocaton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPicture= (ImageView) findViewById(R.id.ivPicture);
        btnSharePicture= (Button) findViewById(R.id.btnSharePicture);
        btnTakePicture= (Button) findViewById(R.id.btnTakePicture);
        tvLocation= (TextView) findViewById(R.id.tvLocation);


        mLocationClient=new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();





        btnTakePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(i,1234);
            }
        });

        btnSharePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try
                {
                    File file=new File(getExternalCacheDir(),"my_image.png");
                    FileOutputStream fOut=new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.PNG,100,fOut);
                    fOut.flush();
                    fOut.close();

                    Intent sharingIntent=new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                    sharingIntent.setType("image/png");
                    String shareBody=tvLocation.getText().toString();
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareBody);
                    startActivity(sharingIntent);


                }catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "Exception : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });



    }//end of onCreate()


    @Override
    protected void onStart() {
        super.onStart();
        if(mLocationClient!=null)
            mLocationClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK)
        {
            if(requestCode==1234)
            {
                photo= (Bitmap) data.getExtras().get("data");
                ivPicture.setImageBitmap(photo);
            }
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

        mLastLocaton=LocationServices.FusedLocationApi.getLastLocation(mLocationClient);
        double lat=mLastLocaton.getLatitude();
        double lon=mLastLocaton.getLongitude();
        String msg="Latitude : "+lat+"\nLongitude : "+lon;
        tvLocation.setText(msg);
    }


    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "Connection suspended", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

        Toast.makeText(this, "Connection Failed", Toast.LENGTH_SHORT).show();
    }
}// end of class
