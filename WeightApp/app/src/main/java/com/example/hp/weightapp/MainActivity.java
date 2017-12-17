package com.example.hp.weightapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText etUserName,etPassword;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUserName= (EditText) findViewById(R.id.etUserName);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);



        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String un=etUserName.getText().toString();
                String pw=etPassword.getText().toString();

                if(un.length()==0)
                {
                    etUserName.setError(" User Name Blank");
                    etUserName.requestFocus();
                    return;
                }
                if(pw.length()==0)
                {
                    etPassword.setError("Password Blank");
                    etPassword.requestFocus();
                    return;
                }


                if(un.equals("abc")&&pw.equals("123"))
                {
                    Intent i=new Intent(MainActivity.this,WeightActivity.class);
                    i.putExtra("un",un);
                    startActivity(i);
                    finish();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Username or Password is incorrect", Toast.LENGTH_SHORT).show();
                    etUserName.setText("");
                    etPassword.setText("");
                    etUserName.requestFocus();

                }




            }
        });


    }
}
