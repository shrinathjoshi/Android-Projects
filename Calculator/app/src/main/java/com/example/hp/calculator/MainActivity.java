package com.example.hp.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText etNumber;
    Button btnSquare,btnSquareRoot,btnCube;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = (EditText) findViewById(R.id.etNumber);
        btnSquare= (Button) findViewById(R.id.btnSquare);
        btnSquareRoot= (Button) findViewById(R.id.btnSquareRoot);
        btnCube= (Button) findViewById(R.id.btnCube);


        btnSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double number=Double.parseDouble(etNumber.getText().toString());
                double r=number*number;
                Toast.makeText(getApplicationContext(), "Square : "+r, Toast.LENGTH_LONG).show();

            }
        });

        btnSquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double number=Double.parseDouble(etNumber.getText().toString());
                double r=Math.sqrt(number);
                Toast.makeText(getApplicationContext(), "Square Root : "+r, Toast.LENGTH_LONG).show();
            }
        });

        btnCube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                double number=Double.parseDouble(etNumber.getText().toString());
                double r=number*number*number;
                Toast.makeText(getApplicationContext(), "Cube : "+r, Toast.LENGTH_LONG).show();

            }
        });
    }
}
