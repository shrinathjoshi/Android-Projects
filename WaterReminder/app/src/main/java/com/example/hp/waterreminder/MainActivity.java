package com.example.hp.waterreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStart,btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnStop=(Button)findViewById(R.id.btnStop);

        btnStart=(Button)findViewById(R.id.btnStart);


        Intent i=new Intent(MainActivity.this,MyReceiver1.class);

        final PendingIntent pi=PendingIntent.getBroadcast(getApplicationContext(),123,i,0);

        final AlarmManager am= (AlarmManager) getSystemService(ALARM_SERVICE);


        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+10000,pi);

            }
        });


        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                am.cancel(pi);


            }
        });


    }

}
