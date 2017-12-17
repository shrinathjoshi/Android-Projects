package com.example.hp.miniproject;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    EditText etUsername,etPassword;
    Button btnLogin,btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUsername= (EditText) findViewById(R.id.etUsername);
        etPassword= (EditText) findViewById(R.id.etPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        btnSignUp= (Button) findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username=etUsername.getText().toString();
                String password=etPassword.getText().toString();

                if(username.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Please Enter a valid Username", Toast.LENGTH_SHORT).show();
                    etUsername.requestFocus();
                    return;
                }
                if(password.length()==0)
                {
                    Toast.makeText(MainActivity.this, "Please Enter a valid Password ", Toast.LENGTH_SHORT).show();
                    etPassword.requestFocus();
                    return;
                }

                ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Authenticating...");
                progressDialog.show();


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


                Intent i =new Intent(MainActivity.this,SecondActivity.class);
                i.putExtra("name",username);
                startActivity(i);

            }
        });



        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(MainActivity.this,SignUpActivity.class);
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

        switch (item.getItemId())
        {
            case R.id.About:
                Snackbar.make(findViewById(android.R.id.content),"Developed by Shrinath",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.Webiste:
                Snackbar.make(findViewById(android.R.id.content),"Clicked Website",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.Follow:
                Snackbar.make(findViewById(android.R.id.content),"Clicked Follow",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.Settings:
                Snackbar.make(findViewById(android.R.id.content),"Clicked Follow",Snackbar.LENGTH_LONG).show();
                break;

            case R.id.Contact:
                Snackbar.make(findViewById(android.R.id.content),"Clicked Follow",Snackbar.LENGTH_LONG).show();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("Do you want to close this Application?");
        builder.setCancelable(false);


        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });


        builder.setNeutralButton("Canel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        AlertDialog alert=builder.create();
        alert.setTitle("Exit");
        alert.show();

    }



}
