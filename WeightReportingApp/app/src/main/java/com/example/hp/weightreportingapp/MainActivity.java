package com.example.hp.weightreportingapp;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.net.URL;

public class MainActivity extends AppCompatActivity {


    EditText etWeight;
    Button btnSubmit;
    FloatingActionButton fabPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        etWeight = (EditText) findViewById(R.id.etWeight);
        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        fabPhone = (FloatingActionButton) findViewById(R.id.fabPhone);


        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wt = etWeight.getText().toString();
                if (wt.length() == 0) {
                    Snackbar.make(view, "Weight is empty", Snackbar.LENGTH_LONG).show();
                    etWeight.requestFocus();
                    return;
                }

                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "My Weight is " + wt);
                startActivity(i);
            }
        });

        fabPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:" + "9820171316"));
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.menu1,menu);


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId()==R.id.about)
        {
            Snackbar.make(findViewById(android.R.id.content),"Developed by Shri",Snackbar.LENGTH_LONG).show();
        }

        if(item.getItemId()==R.id.website)
        {
            Intent i=new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://www.google.com"));
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }
}
