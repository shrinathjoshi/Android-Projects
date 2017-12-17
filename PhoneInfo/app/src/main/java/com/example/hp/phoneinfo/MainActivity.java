package com.example.hp.phoneinfo;

import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    Button btnPhoneInfo;
    TextToSpeech tts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnPhoneInfo= (Button) findViewById(R.id.btnPhoneInfo);

        tts=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

                if(status!=TextToSpeech.ERROR)
                {
                    tts.setLanguage(Locale.ENGLISH);
                }
            }
        });


        btnPhoneInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String model= Build.MODEL;
                String manu=Build.MANUFACTURER;
                String msg=model+" "+manu;
                tts.speak(msg,TextToSpeech.QUEUE_FLUSH,null);

            }
        });


    }
}
