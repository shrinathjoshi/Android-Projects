package com.example.hp.messagingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etMessage;
    Button btnSms,btnEmail,btnSend,btnWhatsApp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etMessage= (EditText) findViewById(R.id.etMessage);
        btnSms= (Button) findViewById(R.id.btnSms);
        btnEmail=(Button)findViewById(R.id.btnEmail);
        btnSend= (Button) findViewById(R.id.btnSend);
        btnWhatsApp= (Button) findViewById(R.id.btnWhatsApp);

        btnSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg=etMessage.getText().toString();
                if(msg.length()==0)
                {
                    etMessage.setError("Message Empty");
                    etMessage.requestFocus();
                    return;
                }

                Intent i=new Intent(getApplicationContext(),SmsActivity.class);
                i.putExtra("msg",msg);
                startActivity(i);

            }
        });


        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg=etMessage.getText().toString();

                if(msg.length()==0)
                {
                    etMessage.setError("Message Empty");
                    etMessage.requestFocus();
                    return;
                }


                Intent i=new Intent(getApplicationContext(),EmailActivity.class);
                i.putExtra("msg",msg);
                startActivity(i);

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=etMessage.getText().toString();
                if(msg.length()==0)
                {
                    etMessage.setError("Message Empty");
                    etMessage.requestFocus();
                    return;
                }

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);

            }
        });


        btnWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=etMessage.getText().toString();
                if(msg.length()==0)
                {
                    etMessage.setError("Message Empty");
                    etMessage.requestFocus();
                    return;
                }

                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.setPackage("com.whatsapp");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                try
                {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    Toast.makeText(MainActivity.this, "WhatsApp not Installed", Toast.LENGTH_LONG).show();
                }

            }
        });


    }//end of onCreate


    public void onBackPressed()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this Application?");
        builder.setCancelable(false);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        builder.setNeutralButton("Canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();

    }


}//end of class
