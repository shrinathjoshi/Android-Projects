package com.example.hp.weightapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ConformActivity extends AppCompatActivity {



    TextView tvInfo;
    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conform);

        tvInfo= (TextView) findViewById(R.id.tvInfo);
        btnLogout= (Button) findViewById(R.id.btnLogout);

        Intent i =getIntent();
        String un=i.getStringExtra("un");
        String wt=i.getStringExtra("wt");
        tvInfo.setText("Username "+un+" Weight "+wt);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
