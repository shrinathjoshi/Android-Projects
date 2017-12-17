package com.example.hp.sweetselection;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SweetActivity extends AppCompatActivity {

    EditText etSweetName;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweet);

        etSweetName= (EditText) findViewById(R.id.etSweetName);
        btnSubmit= (Button) findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String sw=etSweetName.getText().toString();
                if(sw.length()==0)
                {
                    etSweetName.setError("name empty");
                    etSweetName.requestFocus();
                    return;
                }

                Intent i=new Intent();
                i.putExtra("sw",sw);
                setResult(RESULT_OK,i);
                finish();
            }
        });


    }
}
