package com.example.hp.secretcamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView ivFront,ivBack;
    Button btnCapture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ivFront= (ImageView) findViewById(R.id.ivFront);
        ivBack= (ImageView) findViewById(R.id.ivBack);
        btnCapture= (Button) findViewById(R.id.btnCapture);


        btnCapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }
}
