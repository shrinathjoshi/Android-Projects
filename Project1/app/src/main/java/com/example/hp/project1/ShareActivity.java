package com.example.hp.project1;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShareActivity extends AppCompatActivity {

    TextView tvReview;
    Button btnModify,btnWhatsApp,btnEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        tvReview= (TextView) findViewById(R.id.tvReview);

        btnEmail= (Button) findViewById(R.id.btnEmail);
        btnModify= (Button) findViewById(R.id.btnModify);
        btnWhatsApp= (Button) findViewById(R.id.btnWhatsapp);

        Intent i =getIntent();
        final String msg=i.getStringExtra("msg");
        final String email=i.getStringExtra("email");

        tvReview.setText(msg);


        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnWhatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.setPackage("com.whatsapp");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                try {
                    startActivity(i);
                }
                catch (Exception e)
                {
                    Toast.makeText(ShareActivity.this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                }

                }
        });


        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i =new Intent(Intent.ACTION_SENDTO);
                i.setData(Uri.parse("mailto:"+email));
                i.putExtra(Intent.EXTRA_SUBJECT,"Prof Review");
                i.putExtra(Intent.EXTRA_TEXT,msg);
                startActivity(i);

            }
        });








    }
}
