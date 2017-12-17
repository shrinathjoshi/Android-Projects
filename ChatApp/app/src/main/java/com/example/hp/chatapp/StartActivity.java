package com.example.hp.chatapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class StartActivity extends AppCompatActivity {

    Button btnneedaccount,btnhaveaccount;
    TextView tvWelcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnneedaccount= (Button) findViewById(R.id.btnneedAccount);
        btnhaveaccount= (Button) findViewById(R.id.btnhaveAccount);
        tvWelcome= (TextView) findViewById(R.id.tvWelcome);

        btnneedaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent registerIntent =new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        btnhaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent=new Intent(StartActivity.this,loginActivity.class);
                startActivity(loginIntent);
            }
        });



    }
}
