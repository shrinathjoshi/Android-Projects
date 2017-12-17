package com.example.hp.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Scanner;

public class ThirdActivity extends AppCompatActivity {

    Spinner spnOrder;
    TextView tvServices;
    RatingBar rabRating;
    Button btnRate,btnfeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        spnOrder= (Spinner) findViewById(R.id.spnOrder);
        tvServices= (TextView) findViewById(R.id.tvServices);
        rabRating= (RatingBar) findViewById(R.id.rabRating);
        btnRate= (Button) findViewById(R.id.btnRate);
        btnfeedback= (Button) findViewById(R.id.btnFeedback);



        Intent i =getIntent();
        String order=i.getStringExtra("order");

        Scanner sc =new Scanner(order);
        sc.useDelimiter(" ");

        ArrayList<String> hm=new ArrayList<>();
        while(sc.hasNext())
        {
            hm.add(sc.next());
        }

        ArrayAdapter<String > adapter=new ArrayAdapter<String>(ThirdActivity.this,android.R.layout.simple_list_item_1,hm);
        spnOrder.setAdapter(adapter);

        btnRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
         //       String food=(String) spnOrder.getSelectedItem();

                double rate=rabRating.getRating();

                Toast.makeText(ThirdActivity.this, "You gave us "+ rate+" rating.", Toast.LENGTH_SHORT).show();


            }
        });


        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(ThirdActivity.this,FourthActivity.class);
                startActivity(i);

            }
        });


    }
}
