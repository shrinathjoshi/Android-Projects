package com.example.hp.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onButtonTap(View v)
    {
        Toast myToast = Toast.makeText(getApplicationContext(),"Ouch!",Toast.LENGTH_LONG);
        myToast.show();
    }
}
