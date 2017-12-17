package com.example.hp.activitylifecycle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    String tag="SJ123";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(tag,"I am in onCreate method");

}

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(tag,"I am in onResume method");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(tag,"I am in onRestart method");

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(tag,"I am in onStart method");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(tag,"I am in onPause method");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(tag,"I am in onDestroy method");
    }
}
