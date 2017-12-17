package com.example.hp.namegame;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NameActivity extends AppCompatActivity {

    TextView tvWelcome;
    SharedPreferences sp1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);

        tvWelcome= (TextView) findViewById(R.id.tvWelcome);

        sp1=getSharedPreferences("P1",MODE_PRIVATE);

        String name=sp1.getString("n"," ");
        tvWelcome.setText("Welcome "+name);


    }
}
