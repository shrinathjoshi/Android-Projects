package com.example.hp.mycalci;

import android.content.pm.ActivityInfo;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etNumber;
    Button btnSquare,btnSquareRoot,btnCube;
    TextView tvAnswer;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int a = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(a);



        etNumber= (EditText) findViewById(R.id.etNumber);
        btnSquare= (Button) findViewById(R.id.btnSquare);
        btnSquareRoot= (Button) findViewById(R.id.btnSquareRoot);
        btnCube=(Button)findViewById(R.id.btnCube);
        tvAnswer= (TextView) findViewById(R.id.tvAnswer);

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status!=TextToSpeech.ERROR)
                    tts.setLanguage(Locale.ENGLISH);

            }
        });


        btnSquare.setOnClickListener(this);
        btnSquareRoot.setOnClickListener(this);
        btnCube.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String number=etNumber.getText().toString();
        if(number.length()==0)
        {
            Toast.makeText(this, "Number Empty", Toast.LENGTH_SHORT).show();
            etNumber.requestFocus();
            return;
        }


        if(v.getId()==R.id.btnSquare)
        {
            double n=Double.parseDouble(number);
            double r=n*n;
            tvAnswer.setText("Square = "+r);
        }

        if(v.getId()==R.id.btnSquareRoot)
        {
            double n=Double.parseDouble(number);
            double r=Math.sqrt(n);
            tvAnswer.setText("Square Root = "+r);
        }

        if(v.getId()==R.id.btnCube)
        {
            double n=Double.parseDouble(number);
            double r=n*n*n;
            tvAnswer.setText("Cube = "+r);
        }

        tts.speak(tvAnswer.getText().toString(),TextToSpeech.QUEUE_ADD,null);
    }
}
