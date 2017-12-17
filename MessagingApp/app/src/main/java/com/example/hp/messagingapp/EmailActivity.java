package com.example.hp.messagingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EmailActivity extends AppCompatActivity {

    TextView tvEmailMsg;
    EditText etAddress,etSubject;
    Button btnSendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        tvEmailMsg= (TextView) findViewById(R.id.tvEmailMsg);
        etAddress= (EditText) findViewById(R.id.etAddress);
        etSubject= (EditText) findViewById(R.id.etSubject);
        btnSendEmail= (Button) findViewById(R.id.btnSendEmail);

        Intent i=getIntent();
        final String msg=i.getStringExtra("msg");
        tvEmailMsg.setText(msg);

        btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String add=etAddress.getText().toString();
                String sub=etSubject.getText().toString();
                if(add.length()==0)
                {
                    etAddress.setError("Invalid Address");
                    etAddress.requestFocus();
                    return;
                }
                if(sub.length()==0)
                {
                    etSubject.setError("Empty Subject");
                    etSubject.requestFocus();
                    return;
                }

                Intent i=new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+add));
                i.putExtra(Intent.EXTRA_SUBJECT,sub);
                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);


            }
        });


    }
}
