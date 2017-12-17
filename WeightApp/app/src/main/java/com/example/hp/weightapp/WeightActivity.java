package com.example.hp.weightapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WeightActivity extends AppCompatActivity {


    TextView tvUserName;
    EditText etWeight;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight);

        tvUserName= (TextView) findViewById(R.id.tvUserName);
        etWeight= (EditText) findViewById(R.id.etWeight);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);


        Intent i=getIntent();
        final String un =i.getStringExtra("un");
        tvUserName.setText("Welcome "+ un);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wt = etWeight.getText().toString();

                if (wt.length() == 0) {
                    etWeight.setError("Weight is Empty");
                    etWeight.requestFocus();
                    return;
                }


                Intent i = new Intent(WeightActivity.this, ConformActivity.class);
                i.putExtra("un",un);
                i.putExtra("wt", wt);
                startActivity(i);

            }


        });



    }
}
