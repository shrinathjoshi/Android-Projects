package com.example.hp.weightreportingapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class S1Activity extends AppCompatActivity {

    ImageView iv1;
    Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_s1);
        iv1= (ImageView) findViewById(R.id.iv1);
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        iv1.startAnimation(animation);



        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();

            }
        }).start();

    }
}
