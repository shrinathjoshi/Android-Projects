package com.example.hp.waterreminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;

public class MyReceiver1 extends BroadcastReceiver {

    MediaPlayer mp;

    public MyReceiver1() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.

        mp=MediaPlayer.create(context,R.raw.success);
        mp.start();


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);

                    if(mp.isPlaying())
                        mp.stop();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        Intent i=new Intent(context,MyReceiver1.class);

        PendingIntent pi=PendingIntent.getBroadcast(context,123,i,0);

        AlarmManager am= (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        am.set(AlarmManager.RTC_WAKEUP,System.currentTimeMillis()+10000,pi);


    }
}
