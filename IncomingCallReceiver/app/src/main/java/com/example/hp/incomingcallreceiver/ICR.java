package com.example.hp.incomingcallreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;

public class ICR extends BroadcastReceiver {

    String msg=" ";

    public ICR() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String state=intent.getStringExtra(TelephonyManager.EXTRA_STATE);


        if(TelephonyManager.EXTRA_STATE_RINGING.equals(state))
        {
            String num=intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            msg= num+"\n";
        }

        MainActivity.tvLog.setText(MainActivity.tvLog.getText()+msg);

    }
}
