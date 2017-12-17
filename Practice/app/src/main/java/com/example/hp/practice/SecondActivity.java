package com.example.hp.practice;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import static android.R.attr.name;

public class SecondActivity extends AppCompatActivity {

    EditText etv;
    Button btnSubmit,btnSend,btnWeb,btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        etv= (EditText) findViewById(R.id.etv);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);
        btnSend= (Button) findViewById(R.id.btnSend);
        btnWeb= (Button) findViewById(R.id.btnWeb);
        btnEmail= (Button) findViewById(R.id.btnEmail);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String sg=etv.getText().toString();
                Intent i =new Intent();
                i.putExtra("name",sg);
                setResult(RESULT_OK,i);
                finish();

            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(Intent.ACTION_SEND);
                startActivity(i);
            }
        });


        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:shrijoshi97@gmail.com"));
                i.putExtra(Intent.EXTRA_SUBJECT,"Kuch nai");
                i.putExtra(Intent.EXTRA_TEXT,"Chup na Zhatu");
                startActivity(i);
            }
        });


        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("http://www.google.com"));
                startActivity(i);
            }
        });


    }
}
