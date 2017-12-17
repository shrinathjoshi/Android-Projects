package com.kc.calculatorp3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etNumber;
    Button btnSquare, btnSquareRoot, btnCube;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etNumber = (EditText) findViewById(R.id.etNumber);
        btnSquare = (Button) findViewById(R.id.btnSquare);
        btnSquareRoot = (Button) findViewById(R.id.btnSquareRoot);
        btnCube = (Button) findViewById(R.id.btnCube);

        btnSquare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double number = Double.parseDouble(etNumber.getText().toString());
                double square = number * number;
                Toast.makeText(getApplicationContext(), "Square of " + number + " is: " + square, Toast.LENGTH_LONG).show();
            }
        });

        btnSquareRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double number = Double.parseDouble(etNumber.getText().toString());
                double squareRoot = Math.sqrt(number);
                Toast.makeText(getApplicationContext(), "Square root of " + number + " is: " + squareRoot, Toast.LENGTH_LONG).show();
            }
        });

        btnCube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                double number = Double.parseDouble(etNumber.getText().toString());
                double cube = number * number * number;
                Toast.makeText(getApplicationContext(), "Cube of " + number + " is: " + cube, Toast.LENGTH_LONG).show();
            }
        });
    }
}

