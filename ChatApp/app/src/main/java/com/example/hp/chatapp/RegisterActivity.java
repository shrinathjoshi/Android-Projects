package com.example.hp.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    TextInputLayout tilRegName,tilRegEmail,tilRegPassword;
    Button btnCreateAccount;
    TextView tvCreateAccount;

    private Toolbar mtoolbar;
    private DatabaseReference mdatabase;

    private FirebaseAuth mAuth;

    private ProgressDialog RegProgress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        mtoolbar= (Toolbar) findViewById(R.id.register_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        RegProgress=new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        tilRegName= (TextInputLayout) findViewById(R.id.tilRegName);
        tilRegEmail= (TextInputLayout) findViewById(R.id.tilRegEmail);
        tilRegPassword= (TextInputLayout) findViewById(R.id.tilRegPassword);
        btnCreateAccount= (Button) findViewById(R.id.btnCreateAccount);
        tvCreateAccount= (TextView) findViewById(R.id.tvCreateAccount);


        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String displayName=tilRegName.getEditText().getText().toString();
                String email=tilRegEmail.getEditText().getText().toString();
                String password=tilRegPassword.getEditText().getText().toString();


                if(!TextUtils.isEmpty(displayName) || !TextUtils.isEmpty(email)|| !TextUtils.isEmpty(password))
                {
                    RegProgress.setTitle("Registering User");
                    RegProgress.setMessage("Please Wait while we create your Account");
                    RegProgress.setCanceledOnTouchOutside(false);
                    RegProgress.show();

                    createAccount(displayName,email,password);
                }
            }
        });

    }

    private void createAccount(final String displayName, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {

                            FirebaseUser current_user=FirebaseAuth.getInstance().getCurrentUser();
                            String uid=current_user.getUid();

                            String device_token= FirebaseInstanceId.getInstance().getToken();
                            mdatabase=FirebaseDatabase.getInstance().getReference().child("Users").child(uid);

                            HashMap<String,String> userMap=new HashMap<String, String>();
                            userMap.put("name",displayName);
                            userMap.put("status","Hey there  I'm using Let's Chat App ");
                            userMap.put("image","default");
                            userMap.put("thumb_image","default");
                            userMap.put("device_token",device_token);



                            mdatabase.setValue(userMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful())
                                    {
                                        RegProgress.dismiss();
                                        Intent mainIntent=new Intent(RegisterActivity.this,MainActivity.class);
                                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(mainIntent);
                                        finish();

                                    }
                                }
                            });


                        }
                        else
                        {
                            RegProgress.hide();
                            Toast.makeText(RegisterActivity.this," Authentication Error"+task.getResult(),
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });



    }
}
