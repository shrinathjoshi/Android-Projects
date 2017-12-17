package com.example.hp.bluechat;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    final int REQUEST_ENABLE_BT=1;
    TextView tvBluetooth,tvResults;
    Switch swBlue;
    Button btnScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvBluetooth= (TextView) findViewById(R.id.tvBluetooth);
        swBlue= (Switch) findViewById(R.id.swBlue);
        btnScan= (Button) findViewById(R.id.btnScan);
        tvResults= (TextView) findViewById(R.id.tvResults);

        bluetoothAdapter=BluetoothAdapter.getDefaultAdapter();


        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(bluetoothAdapter==null)
                {
                    new AlertDialog.Builder(getApplicationContext()).setMessage("Your device does not supports bluetooth").setTitle("Not Compatable").
                            setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    System.exit(1);
                                }
                            }).setIcon(android.R.drawable.ic_dialog_alert).show();
                }

                if(bluetoothAdapter.isEnabled()==false)
                {
                    Intent i=new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(i,REQUEST_ENABLE_BT);
                }

                Intent discoverableIntent = new Intent(
                        BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
                discoverableIntent.putExtra(
                        BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300);
                startActivity(discoverableIntent);




                BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();

                String str = StringUtils.join(pairedDevices, " ");
                tvResults.setText(str);
            }
        });










    }
}
