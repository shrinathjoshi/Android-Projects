package com.example.hp.miniproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FourthActivity extends AppCompatActivity {

    EditText etMessage;
    Button btnSend,btnLogout,btnWhatsapp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);

        etMessage= (EditText) findViewById(R.id.etMessage);
        btnLogout= (Button) findViewById(R.id.btnLogout);
        btnSend=(Button)findViewById(R.id.btnSend);
        btnWhatsapp= (Button) findViewById(R.id.btnWhatsapp);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg=etMessage.getText().toString();

                if(msg.length()==0)
                {
                    etMessage.setError("Message empty");
                    etMessage.requestFocus();
                    return;
                }

                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);
            }
        });


        btnWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String msg=etMessage.getText().toString();

                if(msg.length()==0)
                {
                    etMessage.setError("Message empty");
                    etMessage.requestFocus();
                    return;
                }


                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.setPackage("com.whatsapp");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                try
                {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    Toast.makeText(FourthActivity.this, "WhatsApp not Installed", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(FourthActivity.this,MainActivity.class);
                startActivity(i);
            }
        });


    }
}
