package com.example.hp.chatapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

public class loginActivity extends AppCompatActivity {

    Toolbar mtoolbar;
    TextInputLayout tilYourEmail,tilYourPassword;
    Button btnLogin;
    TextView tvLogin;

    ProgressDialog loginProgress;
    private DatabaseReference mUserDatabase;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mtoolbar= (Toolbar) findViewById(R.id.login_toolbar);
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setTitle("Login Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tilYourEmail= (TextInputLayout) findViewById(R.id.tilYourEmail);
        tilYourPassword= (TextInputLayout) findViewById(R.id.tilYourPassword);
        btnLogin= (Button) findViewById(R.id.btnLogin);
        tvLogin= (TextView) findViewById(R.id.tvLogin);

        loginProgress=new ProgressDialog(this);

        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email=tilYourEmail.getEditText().getText().toString();
                String password=tilYourPassword.getEditText().getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)) {

                    loginProgress.setTitle("Logging In");
                    loginProgress.setMessage("Please Wait while we Check your credentials");
                    loginProgress.setCanceledOnTouchOutside(false);
                    loginProgress.show();

                    loginUser(email,password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {

    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        loginProgress.dismiss();

                        String current_user_id=mAuth.getCurrentUser().getUid();
                        String deviceToken= FirebaseInstanceId.getInstance().getToken();

                        mUserDatabase.child(current_user_id).child("device_token").setValue(deviceToken).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                Intent i=new Intent(loginActivity.this,MainActivity.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                finish();

                            }
                        });
                    }else {
                        loginProgress.hide();
                        Toast.makeText(loginActivity.this, "Cannot Log In:Please Check the Form and try again", Toast.LENGTH_SHORT).show();
                    }
                }
            });

    }


}
