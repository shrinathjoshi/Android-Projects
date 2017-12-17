package com.example.hp.messagingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SmsActivity extends AppCompatActivity {

    TextView tvSmsMsg;
    EditText etNumber;
    Button btnSendSms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        tvSmsMsg= (TextView) findViewById(R.id.tvSmsMsg);
        etNumber= (EditText) findViewById(R.id.etNumber);
        btnSendSms= (Button) findViewById(R.id.btnSendSms);

        Intent i=getIntent();
        final String msg=i.getStringExtra("msg");
        tvSmsMsg.setText(msg);

        btnSendSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ph=etNumber.getText().toString();
                if(ph.length()==0)
                {
                    etNumber.setError("Invalid Phone Number");
                    etNumber.requestFocus();
                    return;
                }

                Intent i=new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("sms:"+ph));
                i.putExtra("sms body",msg);
                startActivity(i);


            }
        });


    }
}
