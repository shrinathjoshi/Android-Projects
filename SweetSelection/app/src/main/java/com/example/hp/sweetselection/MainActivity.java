package com.example.hp.sweetselection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btnFavoriteSweet,btnOrder;
    TextView tvSweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnFavoriteSweet = (Button) findViewById(R.id.btnFavoriteSweet);
        btnOrder= (Button) findViewById(R.id.btnOrder);
        tvSweet= (TextView) findViewById(R.id.tvSweet);

        btnFavoriteSweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,SweetActivity.class);
                startActivityForResult(i,1234);
            }
        });

        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), tvSweet.getText().toString() + " ordered ", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK&&requestCode==1234&&data!=null)
        {
            String sw=data.getStringExtra("sw");
            tvSweet.setText(sw);
        }


    }
}
