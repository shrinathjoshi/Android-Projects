package com.example.hp.foodfeedback;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    RadioGroup rgFood;
    RatingBar rabRating;
    Button btnSubmit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        rgFood= (RadioGroup) findViewById(R.id.rgFood);
        rabRating= (RatingBar) findViewById(R.id.rabRating);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int f=rgFood.getCheckedRadioButtonId();
                RadioButton rb= (RadioButton) findViewById(f);
                String food=rb.getText().toString();

                String rating=String.valueOf(rabRating.getRating());
                Toast.makeText(getApplicationContext(), food+" "+rating , Toast.LENGTH_SHORT).show();
                
            }
        });

    }
}
