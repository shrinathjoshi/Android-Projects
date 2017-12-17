package com.example.hp.miniproject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SignUpActivity extends AppCompatActivity {

    EditText etName,etEmail,etPassword,etConfirmPassword;
    TextView tvSignup;
    Button btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName= (EditText) findViewById(R.id.etName);
        etEmail= (EditText) findViewById(R.id.etEmail);
        etPassword= (EditText) findViewById(R.id.etPassword);
        etConfirmPassword= (EditText) findViewById(R.id.etConfirmPassword);
        tvSignup= (TextView) findViewById(R.id.tvSignup);
        btnRegister= (Button) findViewById(R.id.btnRegister);



        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ProgressDialog progressDialog=new ProgressDialog(getApplicationContext());
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage(" Registering ...");
                progressDialog.show();


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String name=etName.getText().toString();
                Intent intent =new Intent(SignUpActivity.this,MainActivity.class);

                startActivity(intent);

            }
        });

    }
}
