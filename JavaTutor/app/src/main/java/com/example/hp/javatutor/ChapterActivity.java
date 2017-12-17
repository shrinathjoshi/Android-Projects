package com.example.hp.javatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ChapterActivity extends AppCompatActivity {

    TextView tvChapterContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chapter);

        tvChapterContent= (TextView) findViewById(R.id.tvChapterContent);


        Intent i=getIntent();
        int pos=i.getIntExtra("pos",0);


        try
        {
            String fn="Chapter"+(pos+1)+".txt";
            InputStreamReader isr=new InputStreamReader(getAssets().open(fn));
            BufferedReader br=new BufferedReader(isr);


            StringBuffer sb=new StringBuffer();
            String line;
            while((line=br.readLine())!=null)
            {
                sb.append(line+"\n");
            }

            br.close();
            tvChapterContent.setText(sb.toString());
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Some issues", Toast.LENGTH_SHORT).show();
        }

    }
}
