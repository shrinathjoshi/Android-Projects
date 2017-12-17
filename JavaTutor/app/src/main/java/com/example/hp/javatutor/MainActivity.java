package com.example.hp.javatutor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvChapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvChapter= (ListView) findViewById(R.id.lvChapter);


        ArrayList<String> chap=new ArrayList<>();
        chap.add("Chapter 1 : Java Introduction");
        chap.add("Chapter 2 : Java Operators");
        chap.add("Chapter 3 :  Java Control Statements");

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,chap);

        lvChapter.setAdapter(adapter);


        lvChapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent i1=new Intent(MainActivity.this,ChapterActivity.class);
                i1.putExtra("pos",i);
                startActivity(i1);
            }
        });

    }
}
